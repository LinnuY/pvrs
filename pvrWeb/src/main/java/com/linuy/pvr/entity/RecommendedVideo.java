package com.linuy.pvr.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Entity
@Data
public class RecommendedVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long videoId;
    private Integer score;
}
