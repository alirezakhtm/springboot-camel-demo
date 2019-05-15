package com.khtm.test.camel.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceApiImpl implements BookServiceApi {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<List<Book>> getAllBook() {
        return ResponseEntity.ok(this.bookRepository.findAllBy());
    }

    @Override
    public ResponseEntity<List<Book>> getAllBookByName(String name) {
        return ResponseEntity.ok(this.bookRepository.findAllByName(name));
    }

    @Override
    public ResponseEntity<List<Book>> getAllBookById(Long id) {
        return ResponseEntity.ok(this.bookRepository.findAllById(id));
    }
}
