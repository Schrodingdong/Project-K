package com.schrodingdong.usermanagerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("User")
@Getter @Setter
public class UserModel {
    @Id
    private final String email;
    private String username;
    private String bio;
    @JsonIgnore
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    private Set<UserModel> following = new HashSet<>();
    @JsonIgnore
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.INCOMING)
    private Set<UserModel> followers = new HashSet<>();



    public UserModel(String email, String username, String bio) {
        this.email = email;
        this.username = username;
        this.bio = bio;
    }
}
