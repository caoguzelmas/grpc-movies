package com.caoguzelmas.movie.repository;

import com.caoguzelmas.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> getMovieByGenreOrderByReleaseYearDesc(String genre);
}
