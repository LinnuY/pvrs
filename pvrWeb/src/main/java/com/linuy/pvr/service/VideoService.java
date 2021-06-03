package com.linuy.pvr.service;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.Video;

import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/26
 **/
public interface VideoService {

    ResponseBean<Object> save(Video video);

    ResponseBean<List<Video>> findByPage(int page, int size);

    ResponseBean<Video> findById(Long id);
}
