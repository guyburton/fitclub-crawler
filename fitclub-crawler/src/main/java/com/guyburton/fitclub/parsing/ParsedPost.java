package com.guyburton.fitclub.parsing;

public class ParsedPost {
    private final String message;
    private final String username;

    public ParsedPost(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "ParsedPost{" +
            "username='" + username + '\'' +
            ", message='" + message.substring(0, Math.min(message.length(), 100))+ '\'' +
            '}';
    }
}
