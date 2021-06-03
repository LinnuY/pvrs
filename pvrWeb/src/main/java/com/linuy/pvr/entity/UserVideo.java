package com.linuy.pvr.entity;

import lombok.Data;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Data
public class UserVideo {
    private Long userId;
    private Long videoId;
    private Integer score;
}
