package com.guyburton.fitclub.parsing;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BackLinkFinder {

    private final static Map<String, String> overrideLinks = ImmutableMap.of(
        "https://www.ukclimbing.com/forums/t.php?n=665677", "https://www.ukclimbing.com/forums/walls+training/ukc_fitclub_533-665248",
        "https://www.ukclimbing.com/forums/t.php?n=656751", "https://www.ukclimbing.com/forums/walls+training/fitclub_512-656276"
    );

    private static final String REGEX = "Last week.?s .*?" +
        "(https?://www\\.ukclimbing\\.com/forums/(?:walls\\+training/)?+[\\w\\d\\-_=?\\.]+)";

    public URL findLink(URL url, String body) throws MalformedURLException, BackLinkNotFoundException {
        Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS | Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            String matchedUrl = matcher.group(1);
            if (overrideLinks.containsKey(matchedUrl)) {
                return new URL(overrideLinks.get(matchedUrl));
            }
            URL previousUrl = new URL(matchedUrl);
            if (previousUrl.equals(url)) {
                throw new BackLinkNotFoundException("Circular link found: " + url.toExternalForm());
            }
            return previousUrl;
        }
        throw new BackLinkNotFoundException(url.toString());
    }

}
