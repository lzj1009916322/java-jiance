/**
 *
 */

package com.weservice.catering.wtakeout.modules.sys.oauth2;

import com.weservice.catering.wtakeout.modules.app.entity.CommonUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.sys.entity.SysUserEntity;
import com.weservice.catering.wtakeout.modules.sys.entity.SysUserTokenEntity;
import com.weservice.catering.wtakeout.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 *
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        SysUserEntity user = getUserEntity(tokenEntity);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
//        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
//        simplePrincipalCollection.add("abc", "test");
//        simpleAuthenticationInfo.setPrincipals(simplePrincipalCollection);
//        simpleAuthenticationInfo.setCredentials("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJudWxsIiwiaWF0IjoxNjEzNDQ0ODQwLCJleHAiOjE2MTQwNDk2NDB9.3W3uOX_6fH6FeYBFVFLtaaspHa1emouAKRNyIadVFyUcwMqvC1isgFhEi_brZVkeIFg_EzXf7Khnjtnyau8INw");
//        return simpleAuthenticationInfo;
    }

    /**
     * 查询用户信息
     * @param tokenEntity
     * @return
     */
    private SysUserEntity getUserEntity(SysUserTokenEntity tokenEntity) {
        SysUserEntity user = new SysUserEntity();
        CommonUserInfo commonUserInfo = new CommonUserInfo();
        if (null != tokenEntity.getUserId()) {
            user = shiroService.queryUser(tokenEntity.getUserId());
            commonUserInfo = CommonUserInfo.transfer2CommonUserInfo(user);
            if (user.getStatus() == 0) {
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }
        }
        if (StringUtils.isNotEmpty(tokenEntity.getAppUserId())) {
            commonUserInfo = shiroService.queryAppUSer(tokenEntity.getAppUserId());
        }
        ThreadLocalUserInfo.setUserInfo(commonUserInfo);
        return user;
    }
}
