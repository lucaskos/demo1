package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.dictionaries.SimpleDictionaryDTO;
import com.example.demo.application.model.dictionaries.GenresDictionary;
import com.example.demo.application.model.dictionaries.PersonRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionariesMapper {

    GenresDictionary genresDictionaryToSimpleDictionaryDTO(SimpleDictionaryDTO simpleDictionaryDTO);

    PersonRole personRoleToSimpleDictionaryDTO(SimpleDictionaryDTO simpleDictionaryDTO);
}
