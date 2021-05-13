package com.example.security.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormLoginDto {
    @JsonProperty(value = "userid")
    private String id;

    @JsonProperty(value = "password")
    private String password;
}
