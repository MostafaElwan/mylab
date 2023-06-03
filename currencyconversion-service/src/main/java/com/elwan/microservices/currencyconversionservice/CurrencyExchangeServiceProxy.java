package com.elwan.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.elwan.microservices.currencyconversionservice.security.fiegn.BarAuthZConfiguration;

@FeignClient(name="forex-service", configuration = BarAuthZConfiguration.class)
public interface CurrencyExchangeServiceProxy {
	
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
  
}
