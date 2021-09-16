package com.weservice.catering.wtakeout.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.sys.dao.SysOrganizationDao;
import com.weservice.catering.wtakeout.modules.sys.service.SysOrganizationService;
import com.weservice.catering.wtakeout.modules.sys.vo.SysOrganizationVo;
import com.weservice.catering.wtakeout.modules.sys.constants.OrganizationStatusEnum;
import com.weservice.catering.wtakeout.modules.sys.entity.SysOrganizationEntity;
import com.weservice.catering.wtakeout.modules.sys.service.OrganizationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOrganizationService")
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationDao, SysOrganizationEntity> implements SysOrganizationService {

    @Autowired
    private OrganizationCodeService codeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOrganizationEntity> page = this.page(
                new Query<SysOrganizationEntity>().getPage(params),
                new QueryWrapper<SysOrganizationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageVo(Page<SysOrganizationVo> page, String name, String superName) {
        IPage<SysOrganizationVo> data = getBaseMapper().selectPageVo(page,name,superName);
        return new PageUtils(data);
    }

    @Override
    public Boolean addOrganization(SysOrganizationEntity entity) {
        entity.setLevel(1);
        entity.setStatus(OrganizationStatusEnum.AVAILABLE.getValue());
        Integer code = codeService.getOneCode(1);
        entity.setCode(String.format("%03d",code));
        return save(entity);
    }

}