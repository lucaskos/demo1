package com.example.demo.application.model.cache.dao;


import com.example.demo.application.model.cache.dictionaries.GenresDictionary;
import com.example.demo.commons.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Luke on 01.11.2017.
 */
@Cacheable(CacheConstants.GENRES)
public interface GenresRepo extends JpaRepository<GenresDictionary, Long> {
}
