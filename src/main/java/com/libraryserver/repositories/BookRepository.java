package com.libraryserver.repositories;

import com.libraryserver.model.Book;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepository {
    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    public Book save(Book book){
        books.put(book.getId(), book);
        return book;
    }

    public Book findById(String id){
        return books.get(id);
    }

    public Collection<Book> findAll(){
        return books.values();
    }
}
