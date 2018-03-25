package com.guyburton.fitclub;

import com.guyburton.fitclub.parsing.BackLinkFinder;
import com.guyburton.fitclub.parsing.BackLinkNotFoundException;
import com.guyburton.fitclub.parsing.PageParser;
import com.guyburton.fitclub.parsing.ParsedPage;
import com.guyburton.fitclub.persistence.PageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class Crawler {

    private final PageStore pageStore;
    private final BackLinkFinder backLinkFinder;
    private final PageParser pageParser;

    private final PageLoader pageLoader;

    private int pageAccesses = 0;

    @Autowired
    public Crawler(PageStore pageStore,
                   BackLinkFinder backLinkFinder,
                   PageParser pageParser,
                   PageLoader pageLoader) {
        this.pageStore = pageStore;
        this.backLinkFinder = backLinkFinder;
        this.pageParser = pageParser;
        this.pageLoader = pageLoader;
    }

    public void crawlFrom(URL startingUrl, int maxPages) throws IOException, BackLinkNotFoundException {
        String content = pageLoader.load(startingUrl);

        ParsedPage parsedPage = pageParser.parse(content, startingUrl.toString());

        pageStore.storeWeek(parsedPage);

        URL backLink = backLinkFinder.findLink(startingUrl, content);
        pageAccesses++;
        if (pageAccesses < maxPages) {
            crawlFrom(backLink, maxPages);
        } else {
            System.out.println("Reached end of crawl- " + pageAccesses + " page accesses");
        }
    }

}
