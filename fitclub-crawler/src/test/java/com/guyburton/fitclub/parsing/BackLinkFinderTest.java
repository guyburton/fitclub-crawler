package com.guyburton.fitclub.parsing;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BackLinkFinderTest {

    @Test
    public void testExtractBackLinkFromNewStylePost() throws MalformedURLException, BackLinkNotFoundException {
        URL link = new BackLinkFinder().findLink(new URL("http://www.test.com"),
            "<p>Last week’s thread can be found here: </p><p><a href=\"https://www.ukclimbing.com/forums/walls+training/fitclub_573-680929\">https://www.ukclimbing.com/forums/walls+training/fitclub_573-680929</a></p>");

        assertThat(link.toExternalForm(), equalTo("https://www.ukclimbing.com/forums/walls+training/fitclub_573-680929"));
    }

    @Test
    public void testExtractBackLinkFromOldStylePost() throws MalformedURLException, BackLinkNotFoundException {
        URL link = new BackLinkFinder().findLink(new URL("http://www.test.com"),
            "<p>Last week’s thread can be found here: </p><p><a href=\"https://www.ukclimbing.com/forums/t.php?t=676592\" title=\"UKC Forums - Fitclub 563\">https://www.ukclimbing.com/forums/t.php?t=676592</a></p>");

        assertThat(link.toExternalForm(), equalTo("https://www.ukclimbing.com/forums/t.php?t=676592"));
    }

    @Test
    public void noSpaceOrLink() throws MalformedURLException, BackLinkNotFoundException {
        URL link = new BackLinkFinder().findLink(new URL("http://www.test.com"),
            "<p>Last week’s thread can be found \"here:https://www.ukclimbing.com/forums/walls+training/fitclub_570-679517</p>");
        assertThat(link.toExternalForm(), equalTo("https://www.ukclimbing.com/forums/walls+training/fitclub_570-679517"));
    }
}