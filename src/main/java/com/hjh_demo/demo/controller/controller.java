package com.hjh_demo.demo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh_demo.demo.entity.User;
import com.hjh_demo.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Wrapper;
import java.util.List;

@Controller
@RequestMapping("/")
public class controller {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String index()
    {

        return "index";
    }


    @RequestMapping("/login")
    public String hello(@RequestParam (name = "UserCode") String Usercode,@RequestParam(name = "Password") String Password,ModelMap m1)
    {
       // List<User> userList=userMapper.selectList(null);

       // com.baomidou.mybatisplus.mapper.Wrapper wrapper = new EntityWrapper().eq("UserCode",usercode);

        User user1=new User();
        user1.setUserCode(Usercode);

        User user2 = userMapper.selectOne(user1);

        if(user2.getPassword().equals(Password))
        {
            m1.put("UserCode",user2.getUserCode());
            m1.put("Password",user2.getPassword());
            m1.put("Gender",user2.getGender());
            m1.put("Status",user2.getStatus());
            m1.put("Birthday",user2.getBirthday());
            return  "hello";
        }


        else  return index();
    }

}
