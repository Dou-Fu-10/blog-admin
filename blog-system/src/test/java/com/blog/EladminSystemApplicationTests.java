package com.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        Set<String> post = new HashSet<>();
        post.add("test");
        post.add("test1");
        post.add("test2");
        requestMatchers(post.toArray(new String[0]));
    }
    public static void requestMatchers(String... patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
        System.out.println(patterns.length);
    }
}

