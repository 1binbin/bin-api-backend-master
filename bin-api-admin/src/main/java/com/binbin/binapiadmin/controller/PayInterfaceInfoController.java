/*
 * com.binbin.binapiadmin.controller.PayInterfaceInfoController, 2023-07-22
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.service.UserInterfaceInfoService;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.common.ResultUtils;
import com.binbin.binapicommon.constant.UserInterfaceInfoConstant;
import com.binbin.binapicommon.mode.dto.payInterfaceInfo.PayRequest;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author hongxiaobin
 * @Time 2023/7/22 0022-1:24:46
 */
@RestController
@RequestMapping("/payInterfaceInfo")
@Slf4j
public class PayInterfaceInfoController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private UserService userService;

    @PostMapping("/pay")
    public BaseResponse<Boolean> payInterfaceInfo(@RequestBody PayRequest payRequest, HttpServletRequest request) {
        if (payRequest == null || request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long interfaceInfoId = payRequest.getInterfaceInfoId();
        Integer payNum = payRequest.getPayNum();
        Long userId = loginUser.getId();
        if (interfaceInfoId < 0 || payNum <= 0 || userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo info = userInterfaceInfoService.lambdaQuery()
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId).one();
        if (payNum +info.getLeftNum() > UserInterfaceInfoConstant.MAX_INTERFACE_NUMBER){
            return ResultUtils.fail(ErrorCode.OPERATION_ERROR);
        }
        boolean result = userInterfaceInfoService.lambdaUpdate()
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId)
                .set(UserInterfaceInfo::getLeftNum, info.getLeftNum() + payNum).update();
        return ResultUtils.success(result);
    }
}
