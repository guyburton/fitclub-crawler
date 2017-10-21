package com.guyburton.fitclub.parsing;

public class BackLinkNotFoundException extends Exception {

    public BackLinkNotFoundException(String url) {
        super("No back link found, please manually start crawl from previous post of: " + url);
    }
}
