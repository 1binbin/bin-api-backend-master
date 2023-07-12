/*
 * com.binbin.binapiadmin.controller.FileController, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import com.binbin.binapiadmin.config.QiniuyunOSSConfig;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.common.ResultUtils;
import com.binbin.binapicommon.constant.UserConstant;
import com.binbin.binapicommon.mode.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:08:07
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Resource
    private QiniuyunOSSConfig qiniuyunOSSConfig;

    /**
     * 通用文件单个上传
     *
     * @param file    文件
     * @param request 域对象
     * @return 响应体
     */
    @PostMapping("/upload")
    public BaseResponse<Map<String, Object>> uploadFile(@RequestBody MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(2);
        if (request == null || file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null || user.getId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = user.getId();
        String result = qiniuyunOSSConfig.upload(file, String.valueOf(userId));
        if (result == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        }
        map.put("url", result);
        return ResultUtils.success(map);
    }
}
