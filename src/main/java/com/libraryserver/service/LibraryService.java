package com.libraryserver.service;

import com.libraryserver.model.Book;
import com.libraryserver.model.LoanRecord;
import com.libraryserver.model.User;
import com.libraryserver.repositories.BookRepository;
import com.libraryserver.repositories.LoanRepository;
import com.libraryserver.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LibraryService(UserRepository userRepository,
                          BookRepository bookRepository,
                          LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public User addUser(String id, String name) {
        return userRepository.save(new User(id, name));
    }

    public Book addBook(String id, String title, String author) {
        return bookRepository.save(new Book(id, title, author));
    }

    public boolean borrowBook(String userId, String bookId) {
        if (userRepository.findById(userId) == null) throw new IllegalArgumentException("User not found");
        if (bookRepository.findById(bookId) == null) throw new IllegalArgumentException("Book not found");
        return loanRepository.borrowBook(bookId, userId);
    }

    public boolean returnBook(String userId, String bookId) {
        return loanRepository.returnBook(userId, bookId);
    }

    public Collection<User> listUsers() { return userRepository.findAll(); }
    public Collection<Book> listBooks() { return bookRepository.findAll(); }
    public Map<String,String> currentLoans() { return loanRepository.currentLoans(); }
    public List<LoanRecord> history() { return loanRepository.getHistory(); }
}
