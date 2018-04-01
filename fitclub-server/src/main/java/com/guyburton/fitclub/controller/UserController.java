package com.guyburton.fitclub.controller;

import com.google.common.collect.Ordering;
import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.entities.JpaUser;
import com.guyburton.fitclub.store.repository.PostRepository;
import com.guyburton.fitclub.store.repository.UserRepository;
import com.guyburton.fitclub.viewmodel.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping("/users.html")
    public ModelAndView get() {
        List<JpaUser> users = userRepository.findAll();

        List<JpaUser> usersToDisplay = users.stream()
            .filter(user -> user.getLastPosted().isAfter(LocalDate.of(2018, 1, 1)))
            .sorted(Ordering.natural().onResultOf(u -> u.getUsername().toLowerCase()))
            .collect(Collectors.toList());

        return new ModelAndView("users", "users", usersToDisplay);
    }

    @RequestMapping("/user/{username}")
    public ModelAndView getUserPosts(@PathVariable(name = "username") String username) {
        JpaUser user = userRepository.findOne(username);
        List<JpaPost> posts = postRepository.findByUsername(username);

        return new ModelAndView("user", "user", new UserViewModel(user, posts));
    }
}
