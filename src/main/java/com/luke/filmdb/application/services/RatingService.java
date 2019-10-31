package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RatingDTO;
import com.luke.filmdb.application.commands.ObjectType;
import com.luke.filmdb.application.model.Film;
import com.luke.filmdb.application.model.ObjectRating;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repository.FilmRepo;
import com.luke.filmdb.application.repository.PersonRepo;
import com.luke.filmdb.application.repository.RatingRepo;
import com.luke.filmdb.commons.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepo ratingRepo;
    private final FilmRepo filmRepo;
    private final PersonRepo personRepo;
    private final SecurityUtil securityUtil;
    private final UserService userService;


    public void addRating(RatingDTO ratingDTO) {
        org.springframework.security.core.userdetails.User currentlyLoggedUser = securityUtil.getCurrentlyLoggedUser();
        User user = userService.findOne(currentlyLoggedUser.getUsername());

        switch (ratingDTO.getObjectType()) {
            case FILM:
                Film one = filmRepo.getOne(ratingDTO.getObjectId());
                addFilmRating(one, user);
                break;
            case PERSON:
                Person person = personRepo.getOne(ratingDTO.getObjectId());
                addPersonRating(person, user);
                break;
        }
    }

    private ObjectRating addFilmRating(Film film, User user) {
        ObjectRating rating = setRatingObject(ObjectType.PERSON.toString(), film.getId());
        rating.setUser(user);

        return ratingRepo.save(rating);
    }

    private ObjectRating addPersonRating(Person person, User user) {
        ObjectRating rating = setRatingObject(ObjectType.PERSON.toString(), person.getId());
        rating.setUser(user);

        return ratingRepo.save(rating);
    }

    private ObjectRating setRatingObject(String objectType, Long objectId) {
        ObjectRating rating = new ObjectRating();

        rating.setObjectType(objectType);
        rating.setObjectId(objectId);
        return rating;
    }
}