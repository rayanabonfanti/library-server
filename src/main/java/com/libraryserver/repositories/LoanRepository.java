package com.libraryserver.repositories;

import com.libraryserver.model.LoanRecord;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoanRepository {
    private final Map<String, String> loans = new ConcurrentHashMap<>();
    private final List<LoanRecord> history = Collections.synchronizedList(new ArrayList<>());

    public boolean borrowBook(String bookId, String userId) {
        boolean success = loans.putIfAbsent(bookId, userId) == null;
        if (success) {
            history.add(new LoanRecord(bookId, userId, LoanRecord.Action.BORROW));
        }
        return success;
    }

    public boolean returnBook(String bookId, String userId) {
        boolean success = loans.remove(bookId, userId);
        if (success) {
            history.add(new LoanRecord(bookId, userId, LoanRecord.Action.RETURN));
        }
        return success;
    }

    public Map<String, String> currentLoans() { return loans; }

    public List<LoanRecord> getHistory() {
        history.forEach(LoanRecord::checkExpiration);
        return history;
    }

    public List<LoanRecord> expiredLoans() {
        List<LoanRecord> expired = new ArrayList<>();
        for (LoanRecord lr : history) {
            lr.checkExpiration();
            if (lr.isExpired()) expired.add(lr);
        }
        return expired;
    }
}