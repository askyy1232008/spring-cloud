package com.cloud.consumer.feign;

import org.springframework.stereotype.Component;

import com.cloud.consumer.domain.User;

@Component
public class HystrixClientFallback implements UserFeignClient {
    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setName("王五");
        return user;
    }
}
