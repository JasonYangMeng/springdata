package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.user.UserDto;
import com.abcft.industrygraphmanagement.model.entity.UserEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.UserService;
import com.abcft.industrygraphmanagement.util.JwtUtils;
import com.abcft.industrygraphmanagement.util.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Created by YangMeng on 2021/5/11 15:10
 */
@RestController
@RequestMapping(value = "login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param userEntity
     * @return
     */
    @PostMapping(value = "index")
    public WebResInfo getLogin(@RequestBody UserEntity userEntity) {
        WebResInfo webResInfo = new WebResInfo();
        String loginName = userEntity.getLoginName();
        if (StringUtils.isEmpty(loginName)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("登录名不存在");
            return webResInfo;
        }
        UserDto userDto = new UserDto();
        try {
            UserEntity userByLoginName = userService.getUserByLoginName(loginName);
            if (userByLoginName == null) {
                webResInfo.setCode(WebResCode.Format_Parameter);
                webResInfo.setMessage("登录名不存在");
                return webResInfo;
            }
            String salt = userByLoginName.getSalt();
            String md5 = Md5Utils.getMd5(userEntity.getPwd(), salt);
            if (!userByLoginName.getPwd().equals(md5)) {
                webResInfo.setCode(WebResCode.Format_Parameter);
                webResInfo.setMessage("密码错误");
                return webResInfo;
            }
            webResInfo.setCode(WebResCode.Successful);
            String token = JwtUtils.generate(userByLoginName);
            userDto.setToken(token);
            userDto.setUserName(userByLoginName.getUserName());
            userDto.setImage(userByLoginName.getImage());
            webResInfo.setData(userDto);
        } catch (Exception e) {
            log.error("getLogin:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage("登录异常，请稍候重试");
        }
        return webResInfo;
    }
}
