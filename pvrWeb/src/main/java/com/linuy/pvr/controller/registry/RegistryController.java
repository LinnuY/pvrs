package com.linuy.pvr.controller.registry;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LongTeng
 * @date 2021/02/11
 **/
@Slf4j
@Controller
@RequestMapping("registry")
public class RegistryController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String registry() {
        return "registry";
    }

    @RequestMapping(value = "toRegistry")
    @ResponseBody
    public ResponseBean<Object> toRegistry(HttpServletRequest request) {

        User user = new User();
        user.setNickname(request.getParameter("nickname"));
        user.setPassword(request.getParameter("password"));
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setAddress(request.getParameter("address"));
        user.setPhone(request.getParameter("phone"));
        user.setSex(request.getParameter("sex").equals("1") ? "man" : "woman");

        log.info(user.toString());
        return userService.save(user);
    }
}
