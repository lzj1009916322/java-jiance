package com.weservice.catering.wtakeout.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.common.utils.Constant;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.modules.sys.entity.SysOrganizationEntity;
import com.weservice.catering.wtakeout.modules.sys.service.SysOrganizationService;
import com.weservice.catering.wtakeout.modules.sys.vo.SysOrganizationVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 *
 * @author
 * @email
 * @date 2020-09-14 18:31:42
 */
@RestController
@RequestMapping("/sys/organization")
public class SysOrganizationController extends AbstractController {
    @Autowired
    private SysOrganizationService sysOrganizationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:organization:list")
    public R list(Long page, Long limit, String name, String superName){
        Page<SysOrganizationVo> pageable = new Page<>(page, limit);
        PageUtils pageData = sysOrganizationService.queryPageVo(pageable,name,superName);
        return R.ok().put("page", pageData);
    }

    /**
     * 机构列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:organization:select")
    public R select(){
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的机构列表
        if(getUserId() != Constant.SUPER_ADMIN){
            map.put("create_user_id", getUserId());
        }
        List<SysOrganizationEntity> list = sysOrganizationService.listByMap(map);

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{code}")
    @RequiresPermissions("sys:organization:info")
    public R info(@PathVariable("code") String code){
		SysOrganizationEntity sysOrganization = sysOrganizationService.getById(code);

        return R.ok().put("sysOrganization", sysOrganization);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:organization:save")
    public R save(@RequestBody SysOrganizationEntity sysOrganization){
		sysOrganizationService.addOrganization(sysOrganization);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:organization:update")
    public R update(@RequestBody SysOrganizationEntity sysOrganization){
		sysOrganizationService.updateById(sysOrganization);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:organization:delete")
    public R delete(@RequestBody String[] codes){
		sysOrganizationService.removeByIds(Arrays.asList(codes));

        return R.ok();
    }

}
