package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.utils.Constant;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.constant.LockConstant;
import com.weservice.catering.wtakeout.modules.app.constant.PointEnum;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.req.WithDrawEntity;
import com.weservice.catering.wtakeout.modules.app.service.AppUserInfoService;
import com.weservice.catering.wtakeout.modules.app.service.PointNotesService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.query.QueryUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.weservice.catering.wtakeout.modules.app.constant.PointEnum.WITH_DRAW;


/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@RestController
@RequestMapping("/points/pointNotes")
@Api(value = "积分", tags = "积分")
@Slf4j
public class PointNotesController {
    @Autowired
    private PointNotesService pointNotesService;
    @Autowired
    private AppUserInfoService appUserInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "积分列表")
//    @RequiresPermissions("user:pointnotes:list")
    public Response<PageUtils<PointNotesEntity>> list(@RequestParam Map<String, Object> params) {
        boolean isAdmin = ThreadLocalUserInfo.isAdmin();
        if (!isAdmin) {
            String userId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
            QueryEntityUtil.setUserIdForPara(params, userId);
        }
        PageUtils<PointNotesEntity> page = pointNotesService.queryPage(params);
        if (isAdmin) {
            List<PointNotesEntity> res = page.getList().stream().map(x -> {
                PointNotesEntity entity = (PointNotesEntity) x;
                String userId = entity.getUserId();
                AppUserInfoEntity byId = appUserInfoService.getById(userId);
                if (null != byId) {
                    entity.setAliPayPicUrl(byId.getAliPayPic());
                    entity.setPhone(byId.getPhone());
                    entity.setWechatPicUrl(byId.getWechatPic());
                    entity.setNickName(byId.getNickName());
                }
                return entity;
            }).collect(Collectors.toList());
            page.setList(res);
        }
        return Response.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("积分")
//    @RequiresPermissions("user:pointnotes:info")
    public Response<PointNotesEntity> info(@PathVariable("id") Integer id) {
        PointNotesEntity pointNotes = pointNotesService.getById(id);
        return Response.ok(pointNotes);
    }

    /**
     * 保存
     */
    @PostMapping("/submit")
    @ApiOperation("申请提现")
    @Transactional(rollbackFor = Exception.class)
//    @RequiresPermissions("user:pointnotes:save")
    public Response save(@RequestBody WithDrawEntity withDrawEntity) {
        String userId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
        AppUserInfoEntity appUserInfoEntity = appUserInfoService.getById(userId);
        BigDecimal point = appUserInfoEntity.getPoint();
        if (withDrawEntity.getPoint() == null || withDrawEntity.getPoint().compareTo(new BigDecimal(0)) == 0) {
            throw new RRException("积分兑换数目异常");
        }
        BigDecimal balance = point.subtract(withDrawEntity.getPoint());
        if (balance.compareTo(BigDecimal.valueOf(-0.1)) > 0) {
            LockConstant.pointLock.lock();
            try {
                updateUserInfo(appUserInfoEntity, withDrawEntity, balance);
                savePointNotes(withDrawEntity, userId);
                return Response.ok();
            } catch (Exception e) {
                log.error("申请提现失败", e);
            } finally {
                LockConstant.pointLock.unlock();
            }
        } else {
            throw new RRException("提现失败");
        }
        throw new RRException("提现失败");
    }

    private void updateUserInfo(AppUserInfoEntity appUserInfoEntity, WithDrawEntity withDrawEntity, BigDecimal balance) {
        appUserInfoEntity.setNickName(withDrawEntity.getNickName());
        appUserInfoEntity.setGender(withDrawEntity.getGender());
        appUserInfoEntity.setAvatarUrl(withDrawEntity.getGender());
        appUserInfoEntity.setPoint(balance);
        appUserInfoEntity.setUpdateTime(new Date());
        appUserInfoService.updateEntity(appUserInfoEntity);
    }

    private void savePointNotes(WithDrawEntity withDrawEntity, String userId) {
        PointNotesEntity pointNotes = new PointNotesEntity();
        BeanUtils.copyProperties(withDrawEntity, pointNotes);
        pointNotes.setStatus("doing");
        pointNotes.setType(WITH_DRAW.getType());
        pointNotes.setUserId(userId);
        pointNotes.setCreateTime(new Date());
        pointNotes.setUpdateTime(new Date());
        pointNotes.setMoney(withDrawEntity.getPoint());
        pointNotesService.save(pointNotes);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("user:pointnotes:update")
    public Response update(@RequestBody PointNotesEntity pointNotes) {
        pointNotes.setUpdateTime(new Date());
        pointNotesService.updatePointNotes(pointNotes);
        return Response.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
//    @RequiresPermissions("user:pointnotes:delete")
    public Response delete(@RequestBody Integer[] ids) {
        pointNotesService.removeByIds(Arrays.asList(ids));
        return Response.ok();
    }

}
