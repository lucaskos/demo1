package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.PersonDTO;
import com.luke.filmdb.application.DTO.mappers.EntityMapper;
import com.luke.filmdb.application.model.Person;
import com.luke.filmdb.application.repositories.PersonRepo;
import com.luke.filmdb.commons.MapperCommons;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonServiceTest extends MapperCommons {

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepo personRepo;
    @Mock
    private EntityMapper entityMapper;

    @Test
    public void saveNewPerson() {
        PersonDTO personDTO = getPersonDtoTest();

        Person person = getPerson();

        when(entityMapper.personDTOToPerson(personDTO)).thenReturn(person);
        when(personRepo.save(person)).thenReturn(person);
        when(entityMapper.personToPersonDTO(person)).thenReturn(personDTO);

        PersonDTO savedPerson = personService.addNewPerson(personDTO);

        Mockito.verify(personRepo, Mockito.times(1)).save(person);
        Assert.assertEquals(personDTO.getFirstName(), savedPerson.getFirstName());
    }

    @Test
    public void getPersonById() {
        Person person = getPerson();
        when(personRepo.findById(any())).thenReturn(java.util.Optional.ofNullable(person));
        when(entityMapper.personToPersonDTO(person)).thenReturn(getPersonDtoTest());

        PersonDTO personDTO = personService.getPerson(person.getId());

        Assert.assertEquals(person.getFirstName(), personDTO.getFirstName());
    }

    @Test
    public void findPersonByName() {
        when(personRepo.autocompleteByFirstNameOrLastName(PERSON_FIRST_NAME)).thenReturn(Collections.singletonList(getPerson()));

        List<PersonDTO> byName = personService.findByName(PERSON_FIRST_NAME);

        Assert.assertTrue(byName.size() > 0);
    }

    @Test
    public void findAllPersons() {
        when(personRepo.findAll()).thenReturn(Collections.singletonList(getPerson()));

        List<PersonDTO> allPeople = personService.getAllPeople();

        Assert.assertTrue(allPeople.size() > 0);
    }

    @Test
    public void deletePerson() {
        Person person = getPerson();
        when(personRepo.findById(PERSON_ID)).thenReturn(java.util.Optional.ofNullable(person));

        personService.deletePerson(PERSON_ID);

        verify(personRepo, times(1)).delete(person);
    }
}