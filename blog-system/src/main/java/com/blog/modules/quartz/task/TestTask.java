package com.blog.modules.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试用
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Slf4j
@Component
public class TestTask {

    public void run() {
        log.info("run 执行成功");
    }

    public void run1(String str) {
        log.info("run1 执行成功，参数为： {}" + str);
    }

    public void run2() {
        log.info("run2 执行成功");
    }
}
