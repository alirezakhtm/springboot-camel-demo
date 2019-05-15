package com.khtm.test.camel.bookservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book")
public interface BookServiceApi {

    @RequestMapping(value = "/select-all", method = RequestMethod.GET)



}
