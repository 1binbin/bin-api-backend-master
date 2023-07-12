package com.binbin.binapiadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author hongxiaobin
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
 * @createDate 2023-07-12 16:52:34
 * @Entity generator.domain.UserInterfaceInfo
 */
@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 获取接口调用排名前 n 的接口信息
     * @param limit 前几名
     * @return 响应体
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




