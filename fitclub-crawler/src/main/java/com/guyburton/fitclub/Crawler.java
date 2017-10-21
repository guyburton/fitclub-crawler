package com.guyburton.fitclub;

import com.guyburton.fitclub.parsing.BackLinkFinder;
import com.guyburton.fitclub.parsing.BackLinkNotFoundException;
import com.guyburton.fitclub.parsing.PageParser;
import com.guyburton.fitclub.parsing.ParsedPage;
import com.guyburton.fitclub.store.entities.JpaFitClubWeek;
import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.repository.FitClubWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class Crawler {

    private final FitClubWeekRepository fitClubWeekRepository;

    private final BackLinkFinder backLinkFinder;
    private final PageParser pageParser;

    private final PageLoader pageLoader;

    private int pageAccesses = 0;

    @Autowired
    public Crawler(FitClubWeekRepository fitClubWeekRepository, BackLinkFinder backLinkFinder, PageParser pageParser, PageLoader pageLoader) {
        this.fitClubWeekRepository = fitClubWeekRepository;
        this.backLinkFinder = backLinkFinder;
        this.pageParser = pageParser;
        this.pageLoader = pageLoader;
    }

    public void crawlFrom(URL startingUrl, int maxPages) throws IOException, BackLinkNotFoundException {
        String content = pageLoader.load(startingUrl);

        ParsedPage parsedPage = pageParser.parse(content);

        fitClubWeekRepository.saveAndFlush(new JpaFitClubWeek(
            parsedPage.getWeekId(),
            parsedPage.getPostDate(),
            parsedPage.getPostedBy(),
            startingUrl.toString(),
            parsedPage.getPosts().stream().map(m -> new JpaPost(m.getUsername(), m.getMessage())).collect(Collectors.toList())
        ));

        URL backLink = backLinkFinder.findLink(startingUrl, content);
        pageAccesses++;
        if (pageAccesses < maxPages) {
            crawlFrom(backLink, maxPages);
        }
    }
}
