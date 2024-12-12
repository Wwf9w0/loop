package com.loop.service.persistence.jpa.neo4j.service;

import com.loop.service.persistence.jpa.neo4j.entity.UserNode;
import com.loop.service.persistence.jpa.neo4j.repository.UserNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNeo4jPersistenceService {

    private final UserNodeRepository userNodeRepository;

    public List<UserNode> suggestFriends(Long userId) {
        return userNodeRepository.suggestFriends(userId);
    }

    public List<UserNode> findShortestPathBetweenUsers(Long cUserId, Long oUserId) {
        return userNodeRepository.findShortestPathBetweenUsers(cUserId, oUserId);
    }
}
