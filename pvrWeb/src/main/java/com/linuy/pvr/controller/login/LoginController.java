package com.linuy.pvr.controller.login;

import com.linuy.pvr.common.Constant;
import com.linuy.pvr.common.base.controller.BaseController;
import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.repository.UserRepository;
import com.linuy.pvr.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/02/10
 **/
@Slf4j
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String login(HttpServletRequest request) {
        return "login";
    }

    @RequestMapping("logout")
    public String toLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constant.sessionUsername);
        session.removeAttribute(Constant.sessionPassword);
        return "login";
    }

    @RequestMapping("toLogin")
    @ResponseBody
    public ResponseBean<User> toLogin(HttpServletRequest request, Model model, HttpServletResponse response) {
        ResponseBean<User> responseBean = new ResponseBean<>();
        User user;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordMD5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        try {
            List<User> resultUsers = userRepository.findByUsernameAndPassword(username, passwordMD5);
            if (resultUsers.isEmpty()) {
                responseBean.addError("用户名或密码错误!");
                return responseBean;
            }
            user = resultUsers.get(0);
        } catch (Exception e) {
            log.error("Login:{}", e.toString());
            responseBean.addError("网络异常,请稍后再试!");
            return responseBean;
        }
        //将用户信息保存到Session中用于自登录
        HttpSession session = request.getSession();
        session.setAttribute(Constant.sessionUsername, username);
        session.setAttribute(Constant.sessionPassword, passwordMD5);
        model.addAttribute(user);

        return responseBean.addSuccess(user);
    }
}
