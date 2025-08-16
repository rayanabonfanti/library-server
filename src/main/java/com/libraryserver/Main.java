package com.libraryserver;

import com.libraryserver.model.LoanRecord;
import com.libraryserver.repositories.BookRepository;
import com.libraryserver.repositories.LoanRepository;
import com.libraryserver.repositories.UserRepository;
import com.libraryserver.service.LibraryService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        LibraryService libraryService = getLibraryService(userRepository);

        System.out.println("Status borrowing to user 1: " + libraryService.borrowBook("1", "101"));
        System.out.println("Status borrowing to user 2: " + libraryService.borrowBook("2", "102"));
        System.out.println("Status borrowing to user 3: " + libraryService.borrowBook("3", "102"));

        System.out.println("Current Loans: " + libraryService.currentLoans());

        libraryService.returnBook("101", "1");

        modifyTimeStampToExpired(libraryService);

        System.out.println("History: ");
        libraryService.history().forEach(System.out::println);
    }

    private static void modifyTimeStampToExpired(LibraryService libraryService) {
        libraryService.history().stream()
                .filter(lr -> lr.getAction() == LoanRecord.Action.BORROW)
                .findFirst()
                .ifPresent(lr -> {
                    try {
                        java.lang.reflect.Field field = lr.getClass().getDeclaredField("timestamp");
                        field.setAccessible(true);
                        field.set(lr, Instant.now().minus(8, ChronoUnit.DAYS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private static LibraryService getLibraryService(UserRepository userRepository) {
        BookRepository bookRepository = new BookRepository();
        LoanRepository loanRepository = new LoanRepository();

        LibraryService libraryService = new LibraryService(userRepository, bookRepository, loanRepository);

        libraryService.addUser("1", "Rayana");
        libraryService.addUser("2", "Alice");
        libraryService.addUser("3", "Bob");

        libraryService.addBook("101", "Effective Java", "Joshua Bloch");
        libraryService.addBook("102", "Clean Code", "Robert C. Martin");

        return libraryService;
    }
}