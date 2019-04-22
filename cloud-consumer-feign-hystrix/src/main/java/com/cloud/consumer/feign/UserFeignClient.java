package com.cloud.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.consumer.domain.User;


//@FeignClient(name = "cloud-provider", fallback = HystrixClientFallback.class)
@FeignClient(name = "cloud-provider", fallbackFactory = HystrixClientFactory.class)
public interface UserFeignClient {

  @GetMapping (value = "/user/getUser/{id}")
  public User getUser(@PathVariable("id") Long id);


}
