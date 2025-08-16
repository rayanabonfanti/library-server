package com.libraryserver.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class LoanRecord {
    public enum Action { BORROW, RETURN }

    private final String bookId;
    private final String userId;
    private final Action action;
    private final Instant timestamp;
    private boolean expired;
    private static final int EXPIRATION_DAYS = 7;

    public LoanRecord(String bookId, String userId, Action action) {
        this.bookId = bookId;
        this.userId = userId;
        this.action = action;
        this.timestamp = Instant.now();
        this.expired = false;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public Action getAction() {
        return action;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean isExpired() { return expired; }
    public void checkExpiration() {
        if (action == Action.BORROW && Instant.now().isAfter(timestamp.plus(EXPIRATION_DAYS, ChronoUnit.DAYS))) {
            this.expired = true;
        }
    }

    @Override
    public String toString() {
        String expStr = expired ? " (EXPIRED)" : "";
        return String.format("LoanRepository: %s -> %s: %s at %s%s", userId, bookId, action, timestamp, expStr);
    }

}
