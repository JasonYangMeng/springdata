package com.abcft.industrygraphmanagement.config;

import com.abcft.industrygraphmanagement.dao.mysql.UserMapper;
import com.abcft.industrygraphmanagement.model.entity.UserEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.util.JwtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author Created by YangMeng on 2021/5/11 11:54
 * 登录设置拦截
 */
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Claims claims = JwtUtils.parse(request.getHeader("token"));
        if (claims != null) {
            String userId = String.valueOf(claims.get("userId"));
            UserEntity userEntity = userMapper.getOne(userId);
            if (userEntity != null) {
                UserContext.add(userEntity);
                return true;
            }
        }
        //TODO 做权限控制
        if (request.getRequestURI().contains("sysCollect") || request.getRequestURI().contains("sysSubscribe") || request.getRequestURI().contains("sysLike")) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(WebResCode.Not_Login);
            out.flush();
            out.close();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
