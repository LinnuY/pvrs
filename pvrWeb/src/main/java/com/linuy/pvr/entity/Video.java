package com.linuy.pvr.entity;

import com.linuy.pvr.common.base.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author LongTeng
 * @date 2021/02/09
 **/
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Video extends DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String videoName;
    private String author;
    private String coverImageURI;
    @Column(length = 4098)
    private String description;
    private String videoURI;
    private String status;
}
