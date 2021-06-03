package com.linuy.pvr.repository;

import com.linuy.pvr.entity.RecommendedVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public interface RecommendedVideoRepository extends JpaRepository<RecommendedVideo, Long>, JpaSpecificationExecutor<RecommendedVideo> {
}
