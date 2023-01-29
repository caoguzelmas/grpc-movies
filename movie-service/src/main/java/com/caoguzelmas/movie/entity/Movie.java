package com.caoguzelmas.movie.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class Movie {

    @Id
    private int movieId;
    private String title;
    private int releaseYear;
    private double rating;
    private String genre;
}
