package com.storesmanagementsystem.order.client;

import com.storesmanagementsystem.order.contracts.CommonResponse;
import com.storesmanagementsystem.order.contracts.DealerProductInfoBean;
import com.storesmanagementsystem.order.contracts.ProductInfoBean;
import com.storesmanagementsystem.order.contracts.UserInfoBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "productServiceClient",url = "http://localhost:8082/Product", configuration = ProductServiceClientConfig.class)
public interface ProductServiceClient {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getProduct(@PathVariable("id") int productId);

    @PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestParam("transactionType") String transactionType, ProductInfoBean bean);

    @PostMapping(value = "/Order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addDealerProduct(UserInfoBean bean);

    @GetMapping(value = "/Dealer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getDealerProduct(@PathVariable("id") int productId);

    @GetMapping(value = "/{id}/Dealer/{dealerId}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getDealerProd(@PathVariable("id") int productId, @PathVariable("dealerId") int dealerId);
    
    @PutMapping( value = "/Dealer", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateDealerProduct(@RequestParam("transactionType") String transactionType, DealerProductInfoBean bean);
}
