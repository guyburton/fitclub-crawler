package com.guyburton.fitclub.domain;

import java.net.URL;
import java.time.LocalDate;

public class FitClubWeek {
    private final LocalDate postDate;
    private final int weekNumber;
    private final URL url;

    public FitClubWeek(LocalDate postDate, int weekNumber, URL url) {
        this.postDate = postDate;
        this.weekNumber = weekNumber;
        this.url = url;
    }
}
