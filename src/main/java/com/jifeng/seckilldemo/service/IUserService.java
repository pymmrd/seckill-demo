package com.jifeng.seckilldemo.service;

import com.jifeng.seckilldemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jifeng.seckilldemo.vo.LoginVo;
import com.jifeng.seckilldemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jifeng
 * @since 2022-03-07
 */
public interface IUserService extends IService<User> {

    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response);
}
