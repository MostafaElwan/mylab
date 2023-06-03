package com.elwan.microservices.routerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FallbackController {

    @GetMapping("/fs-fallback")
    Flux<String> getFsFallback() {
        return Flux.just("Sorry, but Forex Service is down now :(");
    }
    
    @GetMapping("/ccs-fallback")
    Flux<String> getCcsFallback() {
        return Flux.just("Sorry, but Currency Conversion Service is down now :(");
    }
}
