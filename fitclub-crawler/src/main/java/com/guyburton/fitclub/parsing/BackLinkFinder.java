package com.guyburton.fitclub.parsing;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BackLinkFinder {

    private static final String REGEX = "Last week.?s .*?" +
        "(https://www\\.ukclimbing\\.com/forums/(?:walls\\+training/)?+[\\w\\d\\-_=?\\.]+)";

    public URL findLink(URL url, String body) throws MalformedURLException, BackLinkNotFoundException {
        Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS | Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            return new URL(matcher.group(1));
        }
        throw new BackLinkNotFoundException(url.toString());
    }

}
