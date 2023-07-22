package com.binbin.binapiadmin.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.binclientsdk.client.BinApiClient;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.exception.ThrowUtil;
import com.binbin.binapiadmin.mapper.InterfaceInfoMapper;
import com.binbin.binapiadmin.service.InterfaceInfoService;
import com.binbin.binapiadmin.service.UserInterfaceInfoService;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapiadmin.utils.SqlUtils;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.constant.CommonConstant;
import com.binbin.binapicommon.constant.InterfaceInfoConstant;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.binbin.binapicommon.mode.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.binbin.binapicommon.mode.entity.InterfaceInfo;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import com.binbin.binapicommon.mode.vo.InterfaceInfoVO;
import com.binbin.binapicommon.mode.vo.RequestParamsRemarkVO;
import com.binbin.binapicommon.mode.vo.ResponseParamsRemarkVO;
import com.binbin.binapicommon.mode.vo.UserVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hongxiaobin
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-07-12 17:02:53
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    private UserService userService;

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 校验接口时候合法
     *
     * @param interfaceInfo 接口信息
     * @param add           是否为添加
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestParams = interfaceInfo.getRequestParams();
        String host = interfaceInfo.getHost();
        String method = interfaceInfo.getMethod();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtil.throwIf(StringUtils.isAnyBlank(name, description, url, host, requestParams, method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > InterfaceInfoConstant.INTERFACEINFO_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest 接口信息查询条件
     * @return 查询条件
     */
    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }
        String name = interfaceInfoQueryRequest.getName();
        String description = interfaceInfoQueryRequest.getDescription();
        String method = interfaceInfoQueryRequest.getMethod();
        Integer status = interfaceInfoQueryRequest.getStatus();
        String searchText = interfaceInfoQueryRequest.getSearchText();
        Date createTime = interfaceInfoQueryRequest.getCreateTime();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        Long id = interfaceInfoQueryRequest.getId();
        Long userId = interfaceInfoQueryRequest.getUserId();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.like("name", searchText).or().like("description", searchText);
        }
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.gt(ObjectUtils.isNotEmpty(createTime), "createTime", createTime);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取接口信息封装
     *
     * @param interfaceInfo 接口信息
     * @param request       域对象
     * @return 封装的接口信息
     */
    @Override
    public InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request) {
        InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
        // 1. 关联查询用户信息
        Long userId = interfaceInfo.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        interfaceInfoVO.setUser(userVO);
        // 封装请求参数说明 和 响应参数说明
        List<RequestParamsRemarkVO> requestParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getRequestParamsRemark()), RequestParamsRemarkVO.class);
        List<ResponseParamsRemarkVO> responseParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getResponseParamsRemark()), ResponseParamsRemarkVO.class);
        interfaceInfoVO.setRequestParamsRemark(requestParamsRemarkVOList);
        interfaceInfoVO.setResponseParamsRemark(responseParamsRemarkVOList);
        return interfaceInfoVO;
    }

    /**
     * 分页获取接口信息封装
     *
     * @param interfaceInfoPage 接口信息
     * @param request           域对象
     * @return 接口信息分页封装
     */
    @Override
    public Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request) {
        List<InterfaceInfo> interfaceInfoList = interfaceInfoPage.getRecords();
        Page<InterfaceInfoVO> interfaceInfoVOPage = new Page<>(interfaceInfoPage.getCurrent(), interfaceInfoPage.getSize(), interfaceInfoPage.getTotal());
        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (CollectionUtils.isEmpty(interfaceInfoList)) {
            return interfaceInfoVOPage;
        }
        // 关联用户信息
        List<Long> userIdSet = interfaceInfoList.stream().map(InterfaceInfo::getUserId).collect(Collectors.toList());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream().collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<InterfaceInfoVO> list = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
            Long userId = interfaceInfo.getUserId();
            // 判断是否当前用户拥有的接口
            boolean isOwnedByCurrentUser = false;

            // 接口调用次数
            UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.lambdaQuery()
                    .eq(UserInterfaceInfo::getUserId, loginUser.getId())
                    .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfo.getId())
                    .one();

            if (userInterfaceInfo != null) {
                isOwnedByCurrentUser = true;
                interfaceInfoVO.setTotalNum(userInterfaceInfo.getTotalNum());
                interfaceInfoVO.setLeftNum(userInterfaceInfo.getLeftNum());
            }

            // 用户信息
            List<RequestParamsRemarkVO> requestParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getRequestParamsRemark()), RequestParamsRemarkVO.class);
            List<ResponseParamsRemarkVO> responseParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getResponseParamsRemark()), ResponseParamsRemarkVO.class);
            interfaceInfoVO.setRequestParamsRemark(requestParamsRemarkVOList);
            interfaceInfoVO.setResponseParamsRemark(responseParamsRemarkVOList);
            // 设置当前用户拥有的接口
            interfaceInfoVO.setIsOwnerByCurrentUser(isOwnedByCurrentUser);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        interfaceInfoVOPage.setRecords(list);
        return interfaceInfoVOPage;
    }

    /**
     * 创建SDK客户端
     *
     * @param request 域对象
     * @return SDK客户端
     */
    @Override
    public BinApiClient getBinApiClient(HttpServletRequest request, InterfaceInfo interfaceInfo,boolean isUser) {
        User loginUser = userService.getLoginUser(request);
        if (isUser) {
            Long userId = loginUser.getId();
            if (interfaceInfo == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            Long interfaceInfoId = interfaceInfo.getId();
            UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.lambdaQuery()
                    .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                    .eq(UserInterfaceInfo::getUserId, userId)
                    .one();
            if (userInterfaceInfo == null || userInterfaceInfo.getLeftNum() <= 0) {
                return null;
            }
        }
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        return new BinApiClient(accessKey, secretKey);
    }

    /**
     * 修改接口信息
     *
     * @param interfaceInfoUpdateRequest 接口信息修改请求
     * @return 是否成功
     */
    @Override
    public boolean updateInterfaceInfo(InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        Long id = interfaceInfoUpdateRequest.getId();
        InterfaceInfo oldInterfaceInfo = this.getById(id);
        ThrowUtil.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        interfaceInfo.setRequestParamsRemark(JSONUtil.toJsonStr(interfaceInfoUpdateRequest.getRequestParamsRemark()));
        interfaceInfo.setResponseParamsRemark(JSONUtil.toJsonStr(interfaceInfoUpdateRequest.getResponseParamsRemark()));

        // 参数校验
        this.validInterfaceInfo(interfaceInfo, false);
        return this.updateById(interfaceInfo);
    }

    /**
     * 根据用户ID 分页获取接口信息封装
     *
     * @param interfaceInfoPage 接口信息分页
     * @param request           当前会话
     * @return 接口信息分页
     */
    @Override
    public Page<InterfaceInfoVO> getInterfaceInfoVOByUserIdPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request) {
        List<InterfaceInfo> interfaceInfoList = interfaceInfoPage.getRecords();
        Page<InterfaceInfoVO> interfaceInfoVOPage = new Page<>(interfaceInfoPage.getCurrent(), interfaceInfoPage.getSize(), interfaceInfoPage.getTotal());
        if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(interfaceInfoList)) {
            return interfaceInfoVOPage;
        }
        // 传入当前用户ID
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        // 过滤掉不是当前用户的接口，并且填充信息
        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream()
                .map(interfaceInfo -> {
                    InterfaceInfoVO interfaceInfoVO = InterfaceInfoVO.objToVo(interfaceInfo);
                    UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.lambdaQuery()
                            .eq(UserInterfaceInfo::getUserId, userId)
                            .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfo.getId())
                            .one();
                    if (userInterfaceInfo != null) {
                        interfaceInfoVO.setTotalNum(userInterfaceInfo.getTotalNum());
                        interfaceInfoVO.setLeftNum(userInterfaceInfo.getLeftNum());
                        // 封装请求参数说明和响应参数说明
                        List<RequestParamsRemarkVO> requestParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getRequestParamsRemark()), RequestParamsRemarkVO.class);
                        List<ResponseParamsRemarkVO> responseParamsRemarkVOList = JSONUtil.toList(JSONUtil.parseArray(interfaceInfo.getResponseParamsRemark()), ResponseParamsRemarkVO.class);
                        interfaceInfoVO.setRequestParamsRemark(requestParamsRemarkVOList);
                        interfaceInfoVO.setResponseParamsRemark(responseParamsRemarkVOList);
                        return interfaceInfoVO;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        System.out.println("==================================================================================");
        System.out.println(interfaceInfoVOList);
        interfaceInfoVOPage.setRecords(interfaceInfoVOList);
        return interfaceInfoVOPage;
    }
}




