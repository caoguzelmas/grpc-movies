package com.caoguzelmas.aggregator.service;

import com.caoguzelmas.aggregator.dto.RecommendedMovie;
import com.caoguzelmas.aggregator.dto.UserGenre;
import com.caoguzelmas.grpc.common.Genre;
import com.caoguzelmas.grpc.movie.MovieSearchRequest;
import com.caoguzelmas.grpc.movie.MovieSearchResponse;
import com.caoguzelmas.grpc.movie.MovieServiceGrpc;
import com.caoguzelmas.grpc.user.UserGenreUpdateRequest;
import com.caoguzelmas.grpc.user.UserResponse;
import com.caoguzelmas.grpc.user.UserSearchRequest;
import com.caoguzelmas.grpc.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String userId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
                .setUserId(userId).build();
        UserResponse userResponse = userServiceBlockingStub.getUserGenre(userSearchRequest);

        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
                .setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = movieServiceBlockingStub.getMovies(movieSearchRequest);

        return movieSearchResponse.getMoviesList().stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setUserId(userGenre.getUserId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase())).build();

        UserResponse userResponse = userServiceBlockingStub.updateUserGenre(userGenreUpdateRequest);
    }
}
