package com.guyburton.fitclub.controller;

import com.google.common.collect.Ordering;
import com.guyburton.fitclub.store.entities.JpaFitClubWeek;
import com.guyburton.fitclub.store.repository.FitClubWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WeekController {

    private final FitClubWeekRepository fitClubWeekRepository;

    @Autowired
    public WeekController(FitClubWeekRepository fitClubWeekRepository) {
        this.fitClubWeekRepository = fitClubWeekRepository;
    }

    @RequestMapping("/week")
    public ModelAndView get() {
        List<JpaFitClubWeek> weeks = fitClubWeekRepository.findAll();

        return new ModelAndView("weeks", "weeks",
            weeks.stream()
            .sorted(Ordering.natural().onResultOf(JpaFitClubWeek::getId))
            .collect(Collectors.toList()));
    }

    @RequestMapping("/week/{id}")
    public ModelAndView getWeek(@PathVariable("id") Integer weekId) {
        JpaFitClubWeek week = fitClubWeekRepository.findOne(weekId);

        return new ModelAndView("week", "week", week);
    }
}
