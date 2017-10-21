package com.guyburton.fitclub;

import com.guyburton.fitclub.store.repository.FitClubWeekRepository;
import com.guyburton.fitclub.store.repository.PostRepository;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private Crawler crawler;

    @Autowired
    private FitClubWeekRepository fitClubWeekRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        Options options = new Options();

        Option crawl = new Option("crawl", true, "Crawls from specified URL");
        crawl.setRequired(false);
        options.addOption(crawl);

        Option clear = new Option("clear", false, "Clear parsed data");
        clear.setRequired(false);
        options.addOption(clear);

        Option maxPagesOption = new Option("maxPages", true, "Max number of pages to crawl");
        maxPagesOption.setRequired(false);
        maxPagesOption.setType(Integer.class);
        options.addOption(maxPagesOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(clear.getOpt())) {
                fitClubWeekRepository.deleteAll();
                fitClubWeekRepository.flush();
                postRepository.deleteAll();
                postRepository.flush();
            }

            String crawlUrl = cmd.getOptionValue(crawl.getOpt());
            if (crawlUrl != null) {
                String maxPages = cmd.getOptionValue(maxPagesOption.getOpt());
                crawler.crawlFrom(new URL(crawlUrl), maxPages == null ? 1 : Integer.parseInt(maxPages));
            }

            System.exit(0);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("fit-club-crawler", options);
            System.exit(1);
        }
    }
}
