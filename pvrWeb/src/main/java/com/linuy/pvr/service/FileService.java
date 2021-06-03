package com.linuy.pvr.service;

import com.linuy.pvr.common.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LongTeng
 * @date 2021/05/18
 **/
public interface FileService {

    ResponseBean<Object> save(MultipartFile file);

    ResponseBean<String> exportUserBehavior();
}
