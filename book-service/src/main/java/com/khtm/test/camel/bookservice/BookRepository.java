package com.khtm.test.camel.bookservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAllBy();
    List<Book> findAllByName(String name);
    List<Book> findAllById(Long id);

}
