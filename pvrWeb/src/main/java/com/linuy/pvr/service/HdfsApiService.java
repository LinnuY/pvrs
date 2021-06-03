package com.linuy.pvr.service;

import java.io.IOException;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public interface HdfsApiService {
    Boolean uploadFileToHDFS(String srcPath, String targetPath);

    Boolean downloadTarget(String srcPath, String targetPath);

}
