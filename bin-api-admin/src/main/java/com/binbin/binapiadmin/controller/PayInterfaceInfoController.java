/*
 * com.binbin.binapiadmin.controller.PayInterfaceInfoController, 2023-07-22
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.common.ResultUtils;
import com.binbin.binapicommon.mode.dto.payInterfaceInfo.PayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author hongxiaobin
 * @Time 2023/7/22 0022-1:24:46
 */
@RestController
@RequestMapping("/payInterfaceInfo")
@Slf4j
public class PayInterfaceInfoController {

    @PostMapping("/pay")
    public BaseResponse<Boolean> payInterfaceInfo(@RequestBody PayRequest payRequest, HttpServletRequest request) {
        return ResultUtils.success(true);
    }
}
