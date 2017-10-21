package com.guyburton.fitclub.store;

import com.google.common.collect.Lists;
import com.guyburton.fitclub.store.entities.JpaFitClubWeek;
import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.repository.FitClubWeekRepository;
import com.guyburton.fitclub.store.repository.PostRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@SpringBootApplication
public class PersistenceTest {

    @ClassRule
    public static final SpringClassRule springRule = new SpringClassRule();

    @Rule
    public SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private FitClubWeekRepository fitClubWeekRepository;

    @Autowired
    private PostRepository postRepository;

    @Before
    public void setup() {
        fitClubWeekRepository.saveAndFlush(new JpaFitClubWeek(
            12, LocalDate.of(2007, 1, 1), "gburton", "http://myurl", Lists.newArrayList(
            new JpaPost("username", "Message")
        )));
    }

    @Transactional
    @Test
    public void cascadePersistenceFromWeekDown() {
        List<JpaFitClubWeek> fitClubWeeks = fitClubWeekRepository.findAll();
        assertThat(fitClubWeeks, hasSize(1));
        JpaFitClubWeek fitClubWeek = getOnlyElement(fitClubWeeks);
        List<JpaPost> posts = fitClubWeek.getPosts();
        assertThat(posts, hasSize(1));
        JpaPost post = getOnlyElement(posts);
        assertThat(post.getMessage(), equalTo("Message"));
    }

    @Transactional
    @Test
    public void cascadePersistenceFromPostUp() {
        List<JpaPost> fitClubWeeks = postRepository.findAll();
        assertThat(fitClubWeeks, hasSize(1));
        JpaPost post = getOnlyElement(fitClubWeeks);
        assertThat(post.getFitClubWeek(), notNullValue());
    }

}
