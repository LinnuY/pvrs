package com.linuy.pvr.service;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public interface BehaviorService {

    Boolean findIsLike(Long userId, Long videoId);

    Boolean findIsSave(Long userId, Long videoId);

    void saveBehavior(Long userId, Long videoId, String behavior);
}
