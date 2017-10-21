package com.guyburton.fitclub.persistence;

import com.guyburton.fitclub.parsing.ParsedPage;
import com.guyburton.fitclub.store.entities.JpaFitClubWeek;
import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.entities.JpaUser;
import com.guyburton.fitclub.store.repository.FitClubWeekRepository;
import com.guyburton.fitclub.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PageStore {

    private final FitClubWeekRepository fitClubWeekRepository;
    private final UserRepository userRepository;

    @Autowired
    public PageStore(FitClubWeekRepository fitClubWeekRepository, UserRepository userRepository) {
        this.fitClubWeekRepository = fitClubWeekRepository;
        this.userRepository = userRepository;
    }

    public void storeWeek(ParsedPage parsedPage) {

        List<JpaPost> posts = parsedPage.getPosts().stream()
            .map(p -> new JpaPost(p.getUsername(), p.getMessage()))
            .collect(Collectors.toList());

        fitClubWeekRepository.saveAndFlush(new JpaFitClubWeek(
            parsedPage.getWeekId(),
            parsedPage.getPostDate(),
            parsedPage.getPostedBy(),
            parsedPage.getUrl(),
            posts
        ));

        posts.stream().forEach(post -> {
            JpaUser user = userRepository.findOne(post.getUsername());
            if (user == null) {
                userRepository.saveAndFlush(new JpaUser(
                    post.getUsername(),
                    post.getWeek().getPostDate(),
                    post.getWeek().getPostDate()));
            } else {
                if (user.getLastPosted().isBefore(post.getWeek().getPostDate())){
                    user.setLastPosted(post.getWeek().getPostDate());
                }
                if (user.getFirstPosted().isAfter(post.getWeek().getPostDate())){
                    user.setFirstPosted(post.getWeek().getPostDate());
                }
                userRepository.saveAndFlush(user);
            }
        });
    }
}
