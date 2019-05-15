package com.khtm.test.camel.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public interface BookServiceApi {

    @RequestMapping(value = "/select-all", method = RequestMethod.GET)
    ResponseEntity<List<Book>> getAllBook();

    @RequestMapping(value = "/select-name/{name}", method = RequestMethod.GET)
    ResponseEntity<List<Book>> getAllBookByName(@PathVariable(name = "name") String name);

    @RequestMapping(value = "/select-id/{id}")
    ResponseEntity<List<Book>> getAllBookById(@PathVariable(name = "id") Long id);

}
