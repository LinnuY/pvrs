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
public class User extends DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(nullable = false, unique = true, length = 13)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(length = 13)
    private String nickname;
    private String sex;
    private String headPUrl;
    private String address;
    private String phone;
    private String email;
    private Integer state = 1;

}
