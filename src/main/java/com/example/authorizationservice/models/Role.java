package com.example.authorizationservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseModel {
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }
}
