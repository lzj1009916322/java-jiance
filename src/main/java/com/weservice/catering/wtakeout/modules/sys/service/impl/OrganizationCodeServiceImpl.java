package com.weservice.catering.wtakeout.modules.sys.service.impl;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.sys.dao.OrganizationCodeDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.weservice.catering.wtakeout.modules.sys.entity.OrganizationCodeEntity;
import com.weservice.catering.wtakeout.modules.sys.service.OrganizationCodeService;


@Service("organizationCodeService")
public class OrganizationCodeServiceImpl extends ServiceImpl<OrganizationCodeDao, OrganizationCodeEntity> implements OrganizationCodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrganizationCodeEntity> page = this.page(
                new Query<OrganizationCodeEntity>().getPage(params),
                new QueryWrapper<OrganizationCodeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Integer getOneCode(Integer level) throws IllegalArgumentException {
        if(level == null){
            throw new IllegalArgumentException();
        }
        QueryWrapper wrapper = new QueryWrapper<OrganizationCodeEntity>();
        wrapper.eq("level",level);
        OrganizationCodeEntity code = this.getOne(wrapper);
        if(code == null){
            code =  new OrganizationCodeEntity();
            code.setCode(1);
            code.setLevel(level);
            this.save(code);
            return code.getCode();
        }
        code.setCode(code.getCode() + 1);
        this.updateById(code);
        return code.getCode();
    }

}