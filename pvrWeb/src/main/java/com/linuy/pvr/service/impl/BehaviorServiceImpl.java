package com.linuy.pvr.service.impl;

import com.linuy.pvr.entity.UserBehaviorLog;
import com.linuy.pvr.repository.UserBehaviorLogRepository;
import com.linuy.pvr.service.BehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Service
public class BehaviorServiceImpl implements BehaviorService {

    @Autowired
    UserBehaviorLogRepository userBehaviorLogRepository;

    @Override
    public Boolean findIsLike(Long userId, Long videoId) {
        List<UserBehaviorLog> log = userBehaviorLogRepository.findUserBehaviorLogByUserIdAndBehaviorAndVideoId(userId, "like", videoId);
        return !log.isEmpty();
    }

    @Override
    public Boolean findIsSave(Long userId, Long videoId) {
        List<UserBehaviorLog> save = userBehaviorLogRepository.findUserBehaviorLogByUserIdAndBehaviorAndVideoId(userId, "save", videoId);
        return !save.isEmpty();
    }

    @Override
    public void saveBehavior(Long userId, Long videoId, String behavior) {
        UserBehaviorLog userBehaviorLog = new UserBehaviorLog();
        userBehaviorLog.setUserId(userId);
        userBehaviorLog.setVideoId(videoId);
        userBehaviorLog.setBehavior(behavior);
        userBehaviorLog.setCreateTime(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
        userBehaviorLog.setUpdateTime(userBehaviorLog.getCreateTime());
        switch (behavior) {
            case "like":
                userBehaviorLog.setScore(3);
                break;
            case "save":
                userBehaviorLog.setScore(5);
                break;
            default:
                userBehaviorLog.setScore(1);
        }
        userBehaviorLogRepository.save(userBehaviorLog);
    }
}
