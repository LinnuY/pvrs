package com.linuy.pvr.service;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;

/**
 * @author LongTeng
 * @date 2021/05/25
 **/
public interface UserService {

    ResponseBean<Object> save(User user);

    ResponseBean<User> findByUsername(String username);
}
