package com.loop.service.persistence.jpa.neo4j.service;

import com.loop.service.persistence.jpa.neo4j.entity.User;
import com.loop.service.persistence.jpa.neo4j.repository.UserNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNeo4jPersistenceService {

    private final UserNeo4jRepository userNeo4jRepository;

    public List<User> suggestFriends(Long userId) {
        return userNeo4jRepository.suggestFriends(userId);
    }

    public List<User> findShortestPathBetweenUsers(Long cUserId, Long oUserId) {
        return userNeo4jRepository.findShortestPathBetweenUsers(cUserId, oUserId);
    }


}
