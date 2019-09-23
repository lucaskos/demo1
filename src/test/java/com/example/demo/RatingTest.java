package com.example.demo;

import com.example.demo.application.DTO.RatingDTO;
import com.example.demo.application.commands.ObjectType;
import com.example.demo.application.model.Film;
import com.example.demo.application.model.user.User;
import com.example.demo.application.repository.FilmRepo;
import com.example.demo.application.repository.PersonRepo;
import com.example.demo.application.repository.RatingRepo;
import com.example.demo.application.services.RatingService;
import com.example.demo.application.services.UserService;
import com.example.demo.commons.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

//https://stackoverflow.com/questions/360520/unit-testing-with-spring-security //todo
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RatingTest {


    @Mock
    private FilmRepo filmRepo;

    @Mock
    private PersonRepo personRepo;

    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private RatingService ratingService;


    @Test
    @WithMockUser(username = "test")
    public void test() {

        //test
        Authentication authentication = Mockito.mock(Authentication.class);
// Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //end test
        Mockito.when(securityUtil.getCurrentlyLoggedUser()).thenReturn(getUser());
        Mockito.when(userService.findOne(any())).thenReturn(User.getInstance());
        Mockito.when(filmRepo.getOne(any())).thenReturn(getFilm());

        ratingService.addRating(getRatingDTO());
    }

    private RatingDTO getRatingDTO() {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setObjectType(ObjectType.FILM);
        ratingDTO.setObjectId(1L);

        return ratingDTO;
    }

    private org.springframework.security.core.userdetails.User getUser() {
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User("test", "test", Collections.emptyList());

        return user;
    }

    private Film getFilm() {
        Film film = new Film();
        film.setId(1L);

        return film;
    }
}
