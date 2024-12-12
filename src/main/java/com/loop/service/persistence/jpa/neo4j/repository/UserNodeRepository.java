package com.loop.service.persistence.jpa.neo4j.repository;

import com.loop.service.persistence.jpa.neo4j.entity.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserNodeRepository extends Neo4jRepository<UserNode, Long> {

    @Query("MATCH (u:UserNode)-(:FRIENDS_WITH)->(friend:UserNode)-[:FRIENDS_WITH]->(suggested:UserNode) " +
            "WHERE u.id = $userId AND NOT (u)-[:FRIENDS_WITH]->(suggested) AND u <> suggested " +
            "RETURN DISTINCT suggested")
    List<UserNode> suggestFriends(Long userId);

    @Query("MATCH path = shortestPath(u1:UserNode)-[:FRIENDS_WITH*]-(u2:UserNode) " +
            "WHERE u1.id = $cUserId AND u2.id = $oUserId " +
            "RETURN nodes(path)")
    List<UserNode> findShortestPathBetweenUsers(Long cUserId, Long oUserId);

    @Query("MATCH (u:UserNode)-[:FRIENDS_WITH*1..2]-(friend:UserNode) " +
            "WHERE u.id = $userId " +
            "RETURN COUNT(DISTINCT friend)")
    int calculateInfluence(Long userId);

    @Query("CALL gds.louvain.stream('socialGraph') " +
            "YIELD nodeId, communityId " +
            "MATCH (u:UserNode) WHERE id(u) = nodeId " +
            "SET u.communityId = communityId " +
            "RETURN u")
    List<Object[]> detectCommunities();
}
