/**
 *
 */

package com.weservice.catering.wtakeout.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.modules.app.utils.JwtUtils;
import com.weservice.catering.wtakeout.modules.sys.dao.SysUserTokenDao;
import com.weservice.catering.wtakeout.modules.sys.entity.SysUserTokenEntity;
import com.weservice.catering.wtakeout.modules.sys.oauth2.TokenGenerator;
import com.weservice.catering.wtakeout.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;
    @Resource
    JwtUtils jwtUtils;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = jwtUtils.generateToken(userId);

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = sysUserTokenDao.queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            sysUserTokenDao.updateByUserId(tokenEntity);
        }

        R r = R.ok().put("token", token).put("expire", EXPIRE);

        return r;
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        sysUserTokenDao.updateByUserId(tokenEntity);
    }

    @Override
    public String createTokenForAppUser(String openId) {
//        String token = jwtUtils.generateToken(openId);
//        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
//        tokenEntity.setAppUserId(openId);
//        tokenEntity.setToken(token);
//        this.save(tokenEntity);
//        return token;
        String token = jwtUtils.generateToken(openId);
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        SysUserTokenEntity tokenEntity = sysUserTokenDao.queryByAppUserId(openId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setAppUserId(openId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            sysUserTokenDao.updateByAppUserId(tokenEntity);
        }
        return token;
    }
}
