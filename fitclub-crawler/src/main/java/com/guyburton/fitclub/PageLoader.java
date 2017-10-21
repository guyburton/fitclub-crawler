package com.guyburton.fitclub;

import com.guyburton.fitclub.persistence.LoadedPage;
import com.guyburton.fitclub.persistence.LoadedPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class PageLoader {

    private final Logger logger = LoggerFactory.getLogger(PageLoader.class);

    private final PageLoadThrottle pageLoadThrottle;
    private final LoadedPageRepository loadedPageRepository;

    @Autowired
    public PageLoader(PageLoadThrottle pageLoadThrottle, LoadedPageRepository loadedPageRepository) {
        this.pageLoadThrottle = pageLoadThrottle;
        this.loadedPageRepository = loadedPageRepository;
    }

    public String load(URL startingUrl) throws IOException {

        LoadedPage previouslyLoadedPage = loadedPageRepository.findOne(startingUrl.toString());
        if (previouslyLoadedPage != null) {
            logger.info("Found cached version of " + startingUrl);
            return previouslyLoadedPage.getContent();
        }

        pageLoadThrottle.throttleIfNecessary();
        logger.info("Accessing " + startingUrl);

        String content;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(startingUrl.openConnection().getInputStream(), StandardCharsets.UTF_8))) {
            content = reader.lines().collect(Collectors.joining("\n"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Downloaded Content:");
            logger.debug(content);
        }

        loadedPageRepository.saveAndFlush(new LoadedPage(startingUrl.toString(), content));

        return content;
    }
}
