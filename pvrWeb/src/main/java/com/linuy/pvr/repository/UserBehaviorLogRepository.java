package com.linuy.pvr.repository;

import com.linuy.pvr.entity.User;
import com.linuy.pvr.entity.UserBehaviorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public interface UserBehaviorLogRepository extends JpaRepository<UserBehaviorLog, Long>, JpaSpecificationExecutor<UserBehaviorLog> {

    List<UserBehaviorLog> findByUserIdAndBehavior(Long userId, String behavior);

    List<UserBehaviorLog> findUserBehaviorLogByUserIdAndBehaviorAndVideoId(Long userId, String behavior, Long videoId);
}
