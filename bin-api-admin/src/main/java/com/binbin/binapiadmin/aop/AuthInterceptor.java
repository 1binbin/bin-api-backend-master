/*
 * com.binbin.binapiadmin.aop.AuthInterceptor, 2023-07-13
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.aop;

import com.binbin.binapiadmin.annotation.AuthCheck;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.enums.UserRoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验拦截器
 *
 * @Author hongxiaobin
 * @Time 2023/7/13 0013-8:41:08
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 进行拦截
     * @param joinPoint 执行点
     * @param authCheck 用户检验
     * @return 放行对象
     * @throws Throwable 异常
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (StringUtils.isAnyBlank(mustRole)) {
            UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(mustRole);
            if (enumByValue == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getUserRole();
            // 被封号直接拒绝
            if (UserRoleEnum.BAN.equals(enumByValue)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"被封号");
            }
            if (UserRoleEnum.ADMIN.equals(enumByValue)) {
                if (!mustRole.equals(userRole)) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 放行
        return joinPoint.proceed();
    }
}
