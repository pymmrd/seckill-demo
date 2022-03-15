package com.jifeng.seckilldemo;


import com.jifeng.seckilldemo.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScript<Boolean> script;

    @Test
    public void testLock01(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //占位，如果key不存在才可以设置成功
        Boolean isLock = valueOperations.setIfAbsent("k1", "v1");
        if(isLock){
            // 如果占位成功进行正常操作
            valueOperations.set("name", "XXXX");
            String name = (String) valueOperations.get("name");
            System.out.println("name=" + name);
            redisTemplate.delete("k1");

        }else {
            System.out.println("已经存在锁");
        }
    }

    @Test
    public void testLock02(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //给锁添加一个过期时间，防止应用在运行过程中抛出异常导致锁无法正常释放。
        Boolean isLock = valueOperations.setIfAbsent("k1", "v1", 5, TimeUnit.SECONDS);
        if(isLock){
            // 如果占位成功进行正常操作
            valueOperations.set("name", "XXXX");
            String name = (String) valueOperations.get("name");
            System.out.println("name=" + name);
            Integer.parseInt("XXXX");
            redisTemplate.delete("k1");
        }else {
            System.out.println("已经存在锁");
        }
    }

    @Test
    public void testLock03(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = UUIDUtil.uuid();
        Boolean isLock = valueOperations.setIfAbsent("k1", value, 5, TimeUnit.SECONDS);
        if(isLock){
            // 如果占位成功进行正常操作
            valueOperations.set("name", "XXXX");
            String name = (String) valueOperations.get("name");
            System.out.println("name=" + name);
            Boolean result = (Boolean) redisTemplate.execute(script, Collections.singletonList("k1"), value);
            System.out.println(result);
        }else {
            System.out.println("已经存在锁");
        }
    }
}
