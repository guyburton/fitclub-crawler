package com.guyburton.fitclub.parsing;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class PageParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public ParsedPage parse(String content, String url) {
        Document document = Jsoup.parse(content);

        String threadTitle = document.select("#thread-title").text();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(threadTitle);
        boolean foundTitle = matcher.find();
        checkArgument(foundTitle, "Could not find week ID in title: " + threadTitle + " from " + url);
        Integer postId = Integer.parseInt(matcher.group());

        Elements elements = document.select("#threadText .author");
        Element topPost = elements.get(0);
        String text = topPost.text();
        Pattern topPostPattern = Pattern.compile("(.+) - on (.+)");
        Matcher topPostMatcher = topPostPattern.matcher(text);
        checkArgument(topPostMatcher.matches(), "Did not understand top post");
        String postedBy = topPostMatcher.group(1);
        String postedAt = topPostMatcher.group(2);

        return new ParsedPage(
            parseDate(postedAt),
            postedBy,
            postId,
            getPosts(document),
            url);
    }

    private LocalDate parseDate(String postedAt) {
        //either 19:33 Sun or 20 Sep 2017
        Pattern timePattern = Pattern.compile("(\\d+:\\d+) Sun");
        Matcher timeMatcher = timePattern.matcher(postedAt);
        if (timeMatcher.find()) {
            LocalDate day = LocalDate.now();
            while(day.getDayOfWeek() != DayOfWeek.SUNDAY) {
                day = day.minusDays(1);
            }
            return day;
        }
        return LocalDate.parse(postedAt, DATE_FORMAT);
    }

    private List<ParsedPost> getPosts(Document document) {

        Elements elements = document.select("#threadText .author,.message");

        List<ParsedPost> posts = Lists.newArrayList();

        int postNumber = 0;
        String currentUser = null;
        for (Element element : elements) {
            postNumber++;
            if (postNumber <= 2) {
                continue;
            }
            Elements author = element.select(".author>a[title=\"View Profile\"");
            if (author.isEmpty()) {
                author = element.select(".author>a[title=\"No Profile\"");
            }
            if (!author.isEmpty()) {
                Element authorLink = author.get(0);
                currentUser = authorLink.text();
            } else {
                String message = element.html();

                Pattern pattern = Pattern.compile("^.*In reply to</a> (.+?): ");
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    message = matcher.replaceFirst("");
                }

                checkNotNull(currentUser, "Parse error - didnt find user in element: " + element);
                posts.add(new ParsedPost(message, currentUser));
                currentUser = null;
            }
        }

        return posts;
    }

}
