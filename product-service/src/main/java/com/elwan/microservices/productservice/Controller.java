package com.elwan.microservices.productservice;

import java.security.Principal;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
	
	@GetMapping("/")
    public String index() {
        return "Welcome to Product Service ...";
    }

    @GetMapping("/getproduct")
    @Secured({"admin"})
    public String getProduct(Principal principal) {
        return "Response from Product Service, User Id:" + principal.getName();
    }

}

