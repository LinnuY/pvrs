package com.linuy.pvr.common.base.controller;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.entity.Video;
import com.linuy.pvr.service.UserService;
import com.linuy.pvr.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/03/03
 **/
@Controller
public abstract class BaseController {

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    public String index(HttpServletRequest request, Model model) {
        String username = (StringUtils.isEmpty(request.getParameter("username"))) ?
                (String) request.getSession().getAttribute("PvrUsername") : request.getParameter("username");
        ResponseBean<User> responseBean = userService.findByUsername(username);
        User user = responseBean.getData();
        model.addAttribute("user", user);

//        int page = (int) request.getAttribute("page");
//        int size = (int) request.getAttribute("size");
        ResponseBean<List<Video>> videoInfo = videoService.findByPage(0, 8);
        List<Video> videos = videoInfo.getData();
        model.addAttribute("videoList", videos);
        return "";
    }


}
