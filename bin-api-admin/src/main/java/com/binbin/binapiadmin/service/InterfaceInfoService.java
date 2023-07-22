package com.binbin.binapiadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.binclientsdk.client.BinApiClient;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.mode.vo.InterfaceInfoVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongxiaobin
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-07-12 16:52:34
 */
@Service
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验接口时候合法
     *
     * @param interfaceInfo 接口信息
     * @param add           是否为添加
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest 接口信息查询条件
     * @return 查询条件
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

    /**
     * 获取接口信息封装
     *
     * @param interfaceInfo 接口信息
     * @param request       域对象
     * @return 封装的接口信息
     */
    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

    /**
     * 分页获取接口信息封装
     *
     * @param interfaceInfoPage 接口信息
     * @param request           域对象
     * @return 接口信息分页封装
     */
    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);

    /**
     * 创建SDK客户端
     *
     * @param request 域对象
     * @return SDK客户端
     */
    BinApiClient getBinApiClient(HttpServletRequest request,InterfaceInfo interfaceInfo);

    /**
     * 修改接口信息
     *
     * @param interfaceInfoUpdateRequest 接口信息修改请求
     * @return 是否成功
     */
    boolean updateInterfaceInfo(InterfaceInfoUpdateRequest interfaceInfoUpdateRequest);

    /**
     * 根据用户ID 分页获取接口信息封装
     *
     * @param interfaceInfoPage 接口信息分页
     * @param request           当前会话
     * @return 接口信息分页
     */
    Page<InterfaceInfoVO> getInterfaceInfoVOByUserIdPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);
}
