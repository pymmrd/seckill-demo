package com.jifeng.seckilldemo.service.impl;

import com.jifeng.seckilldemo.entity.User;
import com.jifeng.seckilldemo.exception.GlobalException;
import com.jifeng.seckilldemo.mapper.UserMapper;
import com.jifeng.seckilldemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jifeng.seckilldemo.utils.CookieUtils;
import com.jifeng.seckilldemo.utils.UUIDUtil;
import com.jifeng.seckilldemo.vo.LoginVo;
import com.jifeng.seckilldemo.vo.RespBean;
import com.jifeng.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jifeng
 * @since 2022-03-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        User user = userMapper.selectById(mobile);
        if(null == user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if(user.getPassword().equals(password) ){
            String ticket = UUIDUtil.uuid();
            //request.getSession().setAttribute(ticket, user);
            redisTemplate.opsForValue().set("user:" + ticket, user);
            CookieUtils.put(request, response, "userTicket", ticket);
            return RespBean.success();
        }else{
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
    }

    @Override
    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:"+ticket);
        if(user != null){
            CookieUtils.put(request, response, "userTicket", ticket);
        }
        return user;
    }
}
