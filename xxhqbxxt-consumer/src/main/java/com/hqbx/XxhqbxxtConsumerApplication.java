package com.hqbx;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class XxhqbxxtConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxhqbxxtConsumerApplication.class, args);
    }

}
