package com.likelion.lionlib.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    BABYLION("ROLE_BABYLION"),
    MANAGER("ROLE_MANAGER"),
    CAPTAIN("ROLE_CAPTAIN");

    private final String value;

    @JsonCreator
    public static Role deserializer(String value) {
        for(Role role : Role.values()){
            if(role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}