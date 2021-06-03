package com.linuy.pvr.service.impl;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.Video;
import com.linuy.pvr.repository.VideoRepository;
import com.linuy.pvr.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author LongTeng
 * @date 2021/05/26
 **/
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoRepository videoRepository;

    @Override
    public ResponseBean<Object> save(Video video) {
        video.setStatus("1");
        ResponseBean<Object> responseBean = new ResponseBean<>();
        try {
            video = videoRepository.save(video);
            return responseBean.addSuccess(video);
        } catch (Exception e) {
            log.error("{}", e.toString());
            return responseBean.addError(e.toString());
        }
    }

    @Override
    public ResponseBean<List<Video>> findByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Video> videoInfos = videoRepository.findAll(pageable);
        List<Video> videoList = videoInfos.toList();
        return new ResponseBean<List<Video>>().addSuccess(videoList);
    }

    public ResponseBean<Video> findById(Long id) {
        ResponseBean<Video> responseBean = new ResponseBean<>();
        Optional<Video> optionalVideo = videoRepository.findById(id);
        if (!optionalVideo.isPresent()) {
            return responseBean.addError("未找到视频信息");
        }
        Video video = optionalVideo.get();

        return responseBean.addSuccess(video);
    }
}
