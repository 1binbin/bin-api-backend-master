/*
 * com.binbin.binapiadmin.controller.AnalysisController, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.controller;

import com.binbin.binapiadmin.annotation.AuthCheck;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.service.InterfaceInfoService;
import com.binbin.binapiadmin.service.UserInterfaceInfoService;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapicommon.common.BaseResponse;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.common.ResultUtils;
import com.binbin.binapicommon.constant.UserConstant;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import com.binbin.binapicommon.mode.vo.InterfaceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计分析接口控制器
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-18:07:47
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserService userService;

    /**
     * 统计接口
     *
     * @return 响应体
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        // 查询调用次数前3名的接口
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo();
        if (userInterfaceInfoList.isEmpty()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口信息不存在");
        }
        // 根据接口id分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 查询所有接口id的接口信息
        List<InterfaceInfo> list = interfaceInfoService.lambdaQuery()
                .in(InterfaceInfo::getId, interfaceInfoIdObjMap.keySet())
                .list();
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口信息不存在");
        }
        // 组装返回结果，统计接口属于当前用户，并且显示5条
        List<InterfaceInfoVO> result = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
            interfaceInfoVO.setTotalNum(interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum());
            return interfaceInfoVO;
        }).filter(detail -> detail.getUserId().equals(loginUser.getId())).limit(5).collect(Collectors.toList());
        return ResultUtils.success(result);
    }
}
