/*
 * com.binbin.binapiadmin.controller.InterfaceInfoController, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.binclientsdk.client.BinApiClient;
import com.binbin.binapiadmin.annotation.AuthCheck;
import com.binbin.binapiadmin.config.GatewayConfig;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.exception.ThrowUtil;
import com.binbin.binapiadmin.service.InterfaceInfoService;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapicommon.common.*;
import com.binbin.binapicommon.constant.CommonConstant;
import com.binbin.binapicommon.constant.UserConstant;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.enums.InterfaceInfoStatusEnum;
import com.binbin.binapicommon.mode.vo.InterfaceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 接口信息控制器
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:08:42
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserService userService;
    @Resource
    private GatewayConfig gatewayConfig;

    /**
     * 创建接口信息
     *
     * @param interfaceInfoAddRequest 请求参数，借口信息
     * @param request                 域对象
     * @return 响应体
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);

        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        interfaceInfo.setRequestParamsRemark(JSONUtil.toJsonStr(interfaceInfoAddRequest.getRequestParamsRemark()));
        interfaceInfo.setResponseParamsRemark(JSONUtil.toJsonStr(interfaceInfoAddRequest.getResponseParamsRemark()));
        interfaceInfo.setPrice(interfaceInfoAddRequest.getPrice());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除接口信息
     *
     * @param deleteRequest 删除请求参数
     * @param request       域对象
     * @return 响应体
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtil.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新接口信息（仅管理员）
     *
     * @param interfaceInfoUpdateRequest 请求参数
     * @return 是否正常更新
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = interfaceInfoService.updateInterfaceInfo(interfaceInfoUpdateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取接口信息
     *
     * @param id 接口id
     * @return 响应体
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVO(interfaceInfo, request));
    }

    /**
     * 分页获取接口信息列表（所有）
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                         HttpServletRequest request) {
        return getListInterfaceInfoVOByUserIdPage(interfaceInfoQueryRequest, request, false);
    }

    /**
     * 分页获取接口信息列表（当前用户）
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/one/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPageForOne(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                               HttpServletRequest request) {
        return getListInterfaceInfoVOByUserIdPage(interfaceInfoQueryRequest, request, true);
    }

    /**
     * 查询我的接口
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @param request                   请求
     * @return 分页列表
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByUserIdPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                               HttpServletRequest request) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtil.throwIf(size > 30, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOByUserIdPage(interfaceInfoPage, request));
    }

    /**
     * 发布接口（仅管理员）——接口已存在
     *
     * @param interfaceInfoInvokeRequest 请求参数，接口信息
     * @param request                    域对象
     * @return 响应体
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 接口是否存在
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo interfaceInfoById = interfaceInfoService.getById(id);
        ThrowUtil.throwIf(interfaceInfoById == null, ErrorCode.NOT_FOUND_ERROR);
        // 接口是否可以调用
        String requestParams = interfaceInfoInvokeRequest.getRequestParams();
        // 接口信息
        String url = interfaceInfoById.getUrl();
        String host = interfaceInfoById.getHost();
        String method = interfaceInfoById.getMethod();
        BinApiClient binApiClient = interfaceInfoService.getBinApiClient(request);
        // 设置网关地址
        binApiClient.setGatewayHost(gatewayConfig.getHost());
        // 验证接口
        try {
            String result = binApiClient.invokeInterface(requestParams, host, url, method);
            if (StringUtils.isAnyBlank(result)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "响应结果为空");
            }
        } catch (Exception e) {
            log.info("接口信息错误：{}", e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }

        // 修改接口状态并上传
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean b = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(b);
    }

    /**
     * 下线（仅管理员）——接口已存在
     *
     * @param idRequest 接口id
     * @return 是否成功
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断接口是否存在
        Long id = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtil.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试调用
     *
     * @param interfaceInfoInvokeRequest 测试调用请求类
     * @return 是否成功
     */
    @PostMapping(value = "/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) throws UnsupportedEncodingException {
        // 校验参数
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo interfaceInfoById = interfaceInfoService.getById(id);
        ThrowUtil.throwIf(interfaceInfoById == null, ErrorCode.NOT_FOUND_ERROR);
        if (interfaceInfoById.getStatus().equals(InterfaceInfoStatusEnum.OFFLINE.getValue())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        // 调用
        String url = interfaceInfoById.getUrl();
        String host = interfaceInfoById.getHost();
        String method = interfaceInfoById.getMethod();
        String requestParams = interfaceInfoInvokeRequest.getRequestParams();
        BinApiClient binApiClient = interfaceInfoService.getBinApiClient(request);
        binApiClient.setGatewayHost(gatewayConfig.getHost());
        String invokeResult;
        try {
            invokeResult = binApiClient.invokeInterface(requestParams, host, url, method);
            if (StringUtils.isAnyBlank(invokeResult)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "响应结果为空");
            }
        } catch (Exception e) {
            log.info("接口信息错误：{}", e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }
        return ResultUtils.success(invokeResult);
    }

    /**
     * 获取用户接口列表
     *
     * @param interfaceInfoQueryRequest 请求参数
     * @param request                   域对象
     * @param isUser                    是否只查询当前用户true——是
     * @return 分页对象
     */
    private BaseResponse<Page<InterfaceInfoVO>> getListInterfaceInfoVOByUserIdPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
                                                                                   HttpServletRequest request, Boolean isUser) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        if (isUser) {
            User loginUser = userService.getLoginUser(request);
            Long userId = loginUser.getId();
            interfaceInfoQueryRequest.setUserId(userId);
        }
        interfaceInfoQueryRequest.setSortField("createTime");
        // 倒序排序
        interfaceInfoQueryRequest.setSortOrder(CommonConstant.SORT_ORDER_DESC);
        // 限制爬虫
        ThrowUtil.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
    }
}
