package com.jifeng.seckilldemo;

import com.jifeng.seckilldemo.entity.User;
import com.jifeng.seckilldemo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserMapper userMapper;

   @Test
   public void createUser(){
       User user = new User();
       user.setId(15611011101L);
       user.setPassword("123456");
       user.setNickname("jifeng");
       userMapper.insert(user);
   }

}
