package com.schrodingdong.usermanagerservice.repository;

import com.schrodingdong.usermanagerservice.model.UserModel;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<UserModel, String> {
}
