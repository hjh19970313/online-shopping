package com.hjh_demo.demo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjh_demo.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserMapper extends BaseMapper<User>{
    @Update("UPDATE user set info = #{s1} WHERE UserCode=#{s2}")
            public void updateinfo (@Param("s1") String s1 ,@Param("s2") String s2)throws Exception;
    @Update("UPDATE user set Password = #{s1} WHERE UserCode=#{s2}")
    public void updatepassword (@Param("s1") String s1 ,@Param("s2") String s2)throws Exception;
}

