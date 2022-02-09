package com.service.demo.client;

import com.service.demo.dto.response.client.ResponseClientDTO;
//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "auth-service")
@RibbonClient(name = "auth-service")
public interface UserClient {
  @RequestMapping(method = RequestMethod.GET, value = "/api/auth/users/{email}")
  ResponseClientDTO<User> getUserByEmail(@PathVariable String email);
}
