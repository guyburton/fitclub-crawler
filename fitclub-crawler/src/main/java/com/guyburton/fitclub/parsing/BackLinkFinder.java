package com.guyburton.fitclub.parsing;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BackLinkFinder {

    public URL findLink(URL url, String body) throws MalformedURLException, BackLinkNotFoundException {
        Pattern pattern = Pattern.compile("Last week.?s thread can be found here:.*?https?://www\\.ukclimbing\\.com/forums/t\\.php\\?\\w=(\\d+)");
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            return new URL("https://www.ukclimbing.com/forums/t.php?n=" + matcher.group(1));
        }
        throw new BackLinkNotFoundException(url.toString());
    }

}
