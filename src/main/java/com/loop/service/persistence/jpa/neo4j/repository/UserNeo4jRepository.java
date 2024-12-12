package com.loop.service.persistence.jpa.neo4j.repository;

import com.loop.service.persistence.jpa.neo4j.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserNeo4jRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User)-(:FRIENDS_WITH)->(friend:User)-[:FRIENDS_WITH]->(suggested:User) " +
            "WHERE u.id = $userId AND NOT (u)-[:FRIENDS_WITH]->(suggested) AND u <> suggested " +
            "RETURN DISTINCT suggested")
    List<User> suggestFriends(Long userId);

    @Query("MATCH path = shortestPath(u1:User)-[:FRIENDS_WITH*]-(u2:User) " +
            "WHERE u1.id = $cUserId AND u2.id = $oUserId " +
            "RETURN nodes(path)")
    List<User> findShortestPathBetweenUsers(Long cUserId, Long oUserId);

    @Query("MATCH (u:User)-[:FRIENDS_WITH*1..2]-(friend:User) " +
            "WHERE u.id = $userId " +
            "RETURN COUNT(DISTINCT friend)")
    int calculateInfluence(Long userId);

    @Query("CALL gds.louvain.stream('socialGraph') " +
            "YIELD nodeId, communityId " +
            "MATCH (u:User) WHERE id(u) = nodeId " +
            "SET u.communityId = communityId " +
            "RETURN u")
    List<Object[]> detectCommunities();
}
