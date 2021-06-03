package com.linuy.pvr.service.impl;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.repository.UserRepository;
import com.linuy.pvr.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/25
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseBean<Object> save(User user) {
        ResponseBean<Object> responseBean = new ResponseBean<>();
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(password);

        try{
            userRepository.save(user);
        } catch (Exception e) {
            responseBean.addError("用户保存失败");
        }
        return responseBean.addSuccess();
    }

    @Override
    public ResponseBean<User> findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            if (StringUtils.isBlank(username)) {
                return new ResponseBean<User>().addError("用户名错误");
            }
        }
        List<User> users = userRepository.findByUsername(username);
        if (users == null || users.isEmpty()) {
            return new ResponseBean<User>().addError("未查询到相关用户");
        }
        return new ResponseBean<User>().addSuccess(users.get(0));
    }
}
