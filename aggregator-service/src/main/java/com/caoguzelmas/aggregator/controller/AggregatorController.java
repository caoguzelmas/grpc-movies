package com.caoguzelmas.aggregator.controller;

import com.caoguzelmas.aggregator.dto.RecommendedMovie;
import com.caoguzelmas.aggregator.dto.UserGenre;
import com.caoguzelmas.aggregator.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private UserMovieService userMovieService;

    @GetMapping("/user/{userId}")
    public List<RecommendedMovie> getMovies(@PathVariable String userId) {
        return userMovieService.getUserMovieSuggestions(userId);
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        this.userMovieService.setUserGenre(userGenre);
    }
}
