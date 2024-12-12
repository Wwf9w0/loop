package com.loop.service.persistence.jpa.neo4j.repository;

import com.loop.service.persistence.jpa.neo4j.entity.UserNetwork;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserNetworkRepository extends Neo4jRepository<UserNetwork, Long> {

    @Query("MATCH (u:UserNetwork)-(:FRIENDS_WITH)->(friend:UserNetwork)-[:FRIENDS_WITH]->(suggested:UserNetwork) " +
            "WHERE u.id = $userId AND NOT (u)-[:FRIENDS_WITH]->(suggested) AND u <> suggested " +
            "RETURN DISTINCT suggested")
    List<UserNetwork> suggestFriends(Long userId);

    @Query("MATCH path = shortestPath(u1:UserNetwork)-[:FRIENDS_WITH*]-(u2:UserNetwork) " +
            "WHERE u1.id = $cUserId AND u2.id = $oUserId " +
            "RETURN nodes(path)")
    List<UserNetwork> findShortestPathBetweenUsers(Long cUserId, Long oUserId);

    @Query("MATCH (u:UserNetwork)-[:FRIENDS_WITH*1..2]-(friend:UserNetwork) " +
            "WHERE u.id = $userId " +
            "RETURN COUNT(DISTINCT friend)")
    int calculateInfluence(Long userId);

    @Query("CALL gds.louvain.stream('socialGraph') " +
            "YIELD nodeId, communityId " +
            "MATCH (u:UserNetwork) WHERE id(u) = nodeId " +
            "SET u.communityId = communityId " +
            "RETURN u")
    List<Object[]> detectCommunities();
}
