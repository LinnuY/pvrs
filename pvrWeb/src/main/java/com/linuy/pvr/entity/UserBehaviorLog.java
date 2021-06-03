package com.linuy.pvr.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author LongTeng
 * @date 2021/02/12
 **/
@Entity
@Data
public class UserBehaviorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long videoId;
    private String behavior;
    private Integer score;
    private String createTime;
    private String updateTime;

    @Override
    public String toString() {
        return userId + "," + videoId + "," + score;
    }
}
