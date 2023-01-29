package com.caoguzelmas.movie.service;

import com.caoguzelmas.grpc.movie.MovieDto;
import com.caoguzelmas.grpc.movie.MovieSearchRequest;
import com.caoguzelmas.grpc.movie.MovieSearchResponse;
import com.caoguzelmas.grpc.movie.MovieServiceGrpc;
import com.caoguzelmas.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {

        List<MovieDto> movieDtoList = movieRepository.getMovieByGenreOrderByReleaseYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getReleaseYear())
                        .setRating(movie.getRating()).build())
                .collect(Collectors.toList());

        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovies(movieDtoList).build());
        responseObserver.onCompleted();
    }
}
