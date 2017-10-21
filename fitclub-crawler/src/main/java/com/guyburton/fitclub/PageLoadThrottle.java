package com.guyburton.fitclub;

import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PageLoadThrottle {

    private static final int pageAccessDelayMs = 3000;
    private final Logger logger = LoggerFactory.getLogger(PageLoadThrottle.class);

    public void throttleIfNecessary() {
        if (logger.isDebugEnabled()) {
            logger.debug("Waiting for " + pageAccessDelayMs + "ms before crawling next link");
        }
        Uninterruptibles.sleepUninterruptibly(pageAccessDelayMs, TimeUnit.MILLISECONDS);
    }
}
