package com.linuy.pvr.service;

import com.linuy.pvr.entity.Video;

import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public interface HistoryService {
    List<Video> findByUser(Long userId);

    void save(Long userId, Long videoId);
}
