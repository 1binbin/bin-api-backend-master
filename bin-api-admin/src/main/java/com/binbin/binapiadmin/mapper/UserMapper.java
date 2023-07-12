package com.binbin.binapiadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binbin.binapicommon.mode.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author hongxiaobin
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-07-12 16:52:34
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




