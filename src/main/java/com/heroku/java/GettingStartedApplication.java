package com.heroku.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;

import java.util.Map;

@SpringBootApplication
@EntityScan(basePackages = {"com.heroku.java.models"}) 
@Controller
@EnableRedisDocumentRepositories
public class GettingStartedApplication {
    @GetMapping("/")
    public String index(Map<String, Object> model) {
        model.put("message", "This is a test");
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(GettingStartedApplication.class, args);
    }
}
