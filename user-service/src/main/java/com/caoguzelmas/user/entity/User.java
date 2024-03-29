package com.caoguzelmas.user.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String name;
    private String genre;
}
