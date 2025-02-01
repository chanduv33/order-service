package com.storesmanagementsystem.order.client;

import com.storesmanagementsystem.order.contracts.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userServiceClient",url = "http://localhost:8085/User", configuration = UserServiceClientConfig.class)
public interface UserServiceClient {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getUser(@PathVariable("id") int id);

}
