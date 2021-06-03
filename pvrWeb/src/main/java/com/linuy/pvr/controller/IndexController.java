package com.linuy.pvr.controller;

import com.linuy.pvr.common.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LongTeng
 * @date 2021/02/10
 **/
@Slf4j
@Controller
@RequestMapping(value = "index")
public class IndexController extends BaseController {

    @RequestMapping
    @Override
    public String index(HttpServletRequest request, Model model) {
        super.index(request, model);
        return "index";
    }
}
