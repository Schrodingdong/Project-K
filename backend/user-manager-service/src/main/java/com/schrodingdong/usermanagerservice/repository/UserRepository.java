package com.schrodingdong.usermanagerservice.repository;

import com.schrodingdong.usermanagerservice.model.UserModel;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends Neo4jRepository<UserModel, String> {
    @Query("MATCH (u:User)-[r:FOLLOWS]->(y:User) WHERE u.email = $userEmail AND y.email = $userToUnfollowEmail DELETE r")
    void deleteFollowBetween(@Param("userEmail") String userEmail, @Param("userToUnfollowEmail") String userToUnfollowEmail);
    
    @Query("MATCH (u:User)-[r:FOLLOWS]->(y:User) WHERE u.email = $userEmail return y")
    List<UserModel> getFollowingList(@Param("userEmail") String userEmail);
    
    @Query("MATCH (u:User)-[r:FOLLOWS]->(y:User) WHERE y.email = $userEmail return u")
    List<UserModel> getFollowerList(@Param("userEmail") String userEmail);
}
