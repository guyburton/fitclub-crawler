package com.guyburton.fitclub.controller;

import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.repository.PostRepository;
import com.guyburton.fitclub.viewmodel.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final PostRepository postStore;

    @Autowired
    public UserController(PostRepository messageStore) {
        this.postStore = messageStore;
    }

    @RequestMapping("/user")
    public ModelAndView get() {
        List<JpaPost> messages = postStore.findAll();

        return new ModelAndView("users", "users",
            messages.stream().map(JpaPost::getUserName).collect(Collectors.toSet()));
    }

    @RequestMapping("/user/{username}")
    public ModelAndView getUserPostsExample(@PathVariable(name = "username") String username) {
        List<JpaPost> messages = postStore.findByUsername(username);

        List<PostViewModel> posts = messages.stream().map(m -> {
            PostViewModel postViewModel = new PostViewModel();
            postViewModel.setMessage(m.getMessage());
            postViewModel.setWeek(m.getFitClubWeek().getId());
            postViewModel.setDate(m.getFitClubWeek().getPostDate());
            postViewModel.setUrl(m.getFitClubWeek().getUrl());
            return postViewModel;
        }).collect(Collectors.toList());

        return new ModelAndView("userPostList", "userPosts", posts);
    }
}
