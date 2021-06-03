package com.linuy.pvr.service.impl;

import com.linuy.pvr.entity.UserBehaviorLog;
import com.linuy.pvr.entity.Video;
import com.linuy.pvr.repository.UserBehaviorLogRepository;
import com.linuy.pvr.repository.VideoRepository;
import com.linuy.pvr.service.HistoryService;
import com.linuy.pvr.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    UserBehaviorLogRepository userBehaviorLogRepository;
    @Autowired
    VideoRepository videoRepository;

    @Override
    public List<Video> findByUser(Long userId) {
        List<UserBehaviorLog> userBehaviorLogs = userBehaviorLogRepository.findByUserIdAndBehavior(userId, "click");
        userBehaviorLogs = new ListUtils<UserBehaviorLog>().removeRepeat(userBehaviorLogs);
        List<Video> videos = new ArrayList<>();
        for (UserBehaviorLog userBehaviorLog : userBehaviorLogs) {
            Optional<Video> videoOptional = videoRepository.findById(userBehaviorLog.getVideoId());
            if (videoOptional.isPresent()) {
                Video video = videoOptional.get();
                videos.add(video);
            }
        }
        return videos;
    }

    @Override
    public void save(Long userId, Long videoId) {
        UserBehaviorLog behaviorLog = new UserBehaviorLog();
        behaviorLog.setUserId(userId);
        behaviorLog.setVideoId(videoId);
        behaviorLog.setBehavior("click");
        behaviorLog.setCreateTime(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
        behaviorLog.setUpdateTime(behaviorLog.getCreateTime());
        behaviorLog.setScore(1);
        userBehaviorLogRepository.save(behaviorLog);
    }


}
