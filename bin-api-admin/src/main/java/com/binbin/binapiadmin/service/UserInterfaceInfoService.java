package com.binbin.binapiadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.binbin.binapicommon.mode.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hongxiaobin
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-07-12 16:52:34
 */
@Service
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验接口
     *
     * @param userInterfaceInfo 用户接口信息
     * @param add               是否为添加
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest 查询条件
     * @return 查询条件
     */
    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest interfaceInfoQueryRequest);


    /**
     * 分页获取接口信息封装
     *
     * @param userInterfaceInfoPage 用户接口分页信息
     * @param request               域对象
     * @return 响应体
     */
    Page<UserInterfaceInfo> getUserInterfaceInfoVOPage(Page<UserInterfaceInfo> userInterfaceInfoPage, HttpServletRequest request);

    /**
     * 获取接口调用排名前 n 的接口信息
     *
     * @param limit 前几名
     * @return 响应体
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}
