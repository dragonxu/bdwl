package com.liaoin.runner;

import com.liaoin.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 */
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private GoodsService goodsService;

    @Override
    public void run(String... args) throws Exception {
        log.info("启动加载商品资源");
        goodsService.synchronization();
    }
}
