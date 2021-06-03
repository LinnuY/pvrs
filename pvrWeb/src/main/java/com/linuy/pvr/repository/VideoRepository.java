package com.linuy.pvr.repository;

import com.linuy.pvr.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LongTeng
 * @date 2021/02/09
 **/
public interface VideoRepository extends JpaRepository<Video, Long> {

}
