package com.linuy.pvr;

import com.linuy.pvr.service.HdfsApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PvrApplicationTests {

    @Autowired
    HdfsApiService hdfsApiService;

    @Test
    void contextLoads() {
    }
}
