package com.example.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String userId = "slolee@naver.com";

    private String password = "cks14579";

    private UserRole userRole = UserRole.USER;

    private Long socialId;

    private String profileHref;
}