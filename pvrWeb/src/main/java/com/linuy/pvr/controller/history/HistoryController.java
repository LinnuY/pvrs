package com.linuy.pvr.controller.history;

import com.linuy.pvr.common.Constant;
import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.common.base.controller.BaseController;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.entity.Video;
import com.linuy.pvr.service.HistoryService;
import com.linuy.pvr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Controller
@Slf4j
@RequestMapping("history")
public class HistoryController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    HistoryService historyService;

    @RequestMapping
    @Override
    public String index(HttpServletRequest request, Model model) {
        super.index(request, model);
        String username = (String) request.getSession().getAttribute(Constant.sessionUsername);
        ResponseBean<User> userResponseBean = userService.findByUsername(username);
        if (userResponseBean.isSuccess()) {
            User user = userResponseBean.getData();
            Long userId = user.getUserId();
            List<Video> videos = historyService.findByUser(userId);
            model.addAttribute("historyVideoList", videos);
        }
        return "history";
    }

}
