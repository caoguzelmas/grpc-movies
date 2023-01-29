package com.caoguzelmas.user.service;

import com.caoguzelmas.grpc.common.Genre;
import com.caoguzelmas.grpc.user.UserGenreUpdateRequest;
import com.caoguzelmas.grpc.user.UserResponse;
import com.caoguzelmas.grpc.user.UserSearchRequest;
import com.caoguzelmas.grpc.user.UserServiceGrpc;
import com.caoguzelmas.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userBuilder = UserResponse.newBuilder();
        userRepository.findById(request.getUserId())
                .ifPresent(user -> {
                    userBuilder.setName(user.getName())
                            .setUserId(user.getUserId())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });

        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder userBuilder = UserResponse.newBuilder();
        userRepository.findById(request.getUserId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    userBuilder.setName(user.getName())
                            .setUserId(user.getUserId())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });

        responseObserver.onNext(userBuilder.build());
        responseObserver.onCompleted();
    }
}
