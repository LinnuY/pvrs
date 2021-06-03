package com.linuy.pvr.controller.video;

import com.linuy.pvr.common.Constant;
import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.common.base.controller.BaseController;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.entity.Video;
import com.linuy.pvr.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author LongTeng
 * @date 2021/03/12
 **/
@Slf4j
@Controller
@RequestMapping(value = "video")
public class VideoController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BehaviorService behaviorService;

    private static final String URI_PATH = "/static/videos";

    @RequestMapping
    @Override
    public String index(HttpServletRequest request, Model model) {
        super.index(request, model);
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute(Constant.sessionUsername);
        User user = userService.findByUsername(userName).getData();
        String videoId = request.getParameter("videoId");
        if (StringUtils.isNotBlank(videoId)) {
            Long id = Long.parseLong(videoId);
            ResponseBean<Video> responseBean = videoService.findById(id);
            Video video = responseBean.getData();
            model.addAttribute("video", video);
            Boolean isLike = behaviorService.findIsLike(user.getUserId(), id);
            model.addAttribute("isLike", isLike);
            Boolean isSave = behaviorService.findIsSave(user.getUserId(), id);
            model.addAttribute("isSave", isSave);
            clickLog(user.getUserId(), id);
        }
        return "videoPage";
    }

    @RequestMapping("upload")
    public String uploadIndex(HttpServletRequest request, Model model) {
        super.index(request, model);
        return "videoUpload";
    }

    @RequestMapping(value = "uploadAction")
    @ResponseBody
    public ResponseBean<Object> uploadVideo(MultipartHttpServletRequest multipartHttpServletRequest) {
        Video video = new Video();
        MultipartFile videoFile = multipartHttpServletRequest.getFile("videoFile");
        ResponseBean<Object> responseBean = fileService.save(videoFile);
        if (responseBean.isError()) {
            return responseBean;
        }
        video.setVideoURI(URI_PATH + "/" + responseBean.getMessage());

        MultipartFile coverImageFile = multipartHttpServletRequest.getFile("coverImageFile");
        responseBean = fileService.save(coverImageFile);
        if (responseBean.isError()) {
            return responseBean;
        }
        video.setCoverImageURI(URI_PATH + "/" + responseBean.getMessage());
        video.setTitle(multipartHttpServletRequest.getParameter("title"));
        video.setVideoName(multipartHttpServletRequest.getParameter("videoName"));
        video.setDescription(multipartHttpServletRequest.getParameter("description"));
        video.setAuthor(multipartHttpServletRequest.getParameter("username"));
        videoService.save(video);

        return new ResponseBean<>().addSuccess();
    }

    @RequestMapping("likes")
    @ResponseBody
    public ResponseBean<Object> likes(HttpServletRequest request) {
        String videoIdString = request.getParameter("videoId");
        long videoId = Long.parseLong(videoIdString);
        String userIdString = request.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        behaviorService.saveBehavior(userId, videoId, "like");
        return new ResponseBean<>().addSuccess();
    }

    @RequestMapping("saves")
    @ResponseBody
    public ResponseBean<Object> saves(HttpServletRequest request) {
        String videoIdString = request.getParameter("videoId");
        long videoId = Long.parseLong(videoIdString);
        String userIdString = request.getParameter("userId");
        long userId = Long.parseLong(userIdString);
        behaviorService.saveBehavior(userId, videoId, "save");
        return new ResponseBean<>().addSuccess();
    }

    private void clickLog(Long userId, Long videoId) {
        historyService.save(userId, videoId);
    }
}
