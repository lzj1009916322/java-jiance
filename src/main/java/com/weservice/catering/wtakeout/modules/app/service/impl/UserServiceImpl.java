/**
 *
 */

package com.weservice.catering.wtakeout.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.validator.Assert;
import com.weservice.catering.wtakeout.modules.app.dao.UserDao;
import com.weservice.catering.wtakeout.modules.app.entity.UserEntity;
import com.weservice.catering.wtakeout.modules.app.form.LoginForm;
import com.weservice.catering.wtakeout.modules.app.service.UserService;
import com.weservice.catering.wtakeout.modules.app.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserEntity queryByMobile(String mobile) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
    }

    @Override
    public long login(LoginForm form) {
//		UserEntity user = queryByMobile(form.getMobile());
//		Assert.isNull(user, "手机号或密码错误");
//
//		//密码错误
//		if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
//			throw new RRException("手机号或密码错误");
//		}
//
//		return user.getUserId();
        return 123;
    }
}
