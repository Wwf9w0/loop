package com.loop.service.service.game;

import com.loop.service.persistence.jpa.neo4j.entity.UserNode;
import com.loop.service.persistence.jpa.neo4j.service.UserNodePersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RandomGameService {

    private final UserNodePersistenceService userNodePersistenceService;
    private static final Map<String, List<UserNode>> localCache = new ConcurrentHashMap<>();
    private static final Map<String, List<UserNode>> localCache2 = new ConcurrentHashMap<>();
    private static final String LOCAL_CACHE_KEY = "localCacheKey";
    private static final String LOCAL_CACHE_KEY2 = "localCacheKey2";

    public List<UserNode> randomGame(Long userId) {
        List<List<UserNode>> nodes = userNodePersistenceService.recommentCommunities();
        List<UserNode> userList = userNodePersistenceService.suggestFriends(userId);

        List<UserNode> newList = new ArrayList<>();

        nodes.forEach(node -> node.forEach(n -> {
            if (userList.contains(n)) {
                newList.add(n);
            }
        }));
        return newList;
    }

    public void recommendUser(Long userId) {
        List<UserNode> recommentUser = randomGame(userId);
        List<UserNode> readyUser = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserNode user = recommentUser.get(i);
            readyUser.add(user);
            int random = new Random().nextInt(10);
            UserNode randomUser = readyUser.get(random);
            List<UserNode> cache = localCache.getOrDefault(LOCAL_CACHE_KEY, new ArrayList<>());
            if (cache.contains(randomUser)) {
                sendEvent(userId, randomUser.getId());
                System.out.println("Random User is Ready! " + randomUser);
            }
            break;
        }
    }

    private void sendEvent(Long uuId1, Long uuId2) {
        UserNode u1 = userNodePersistenceService.findById(uuId1);
        UserNode u2 = userNodePersistenceService.findById(uuId2);
        List<UserNode> userNodes = new ArrayList<>();
        userNodes.add(u1);
        userNodes.add(u2);
        localCache.put(LOCAL_CACHE_KEY, userNodes);
        localCache2.put(LOCAL_CACHE_KEY2, Collections.singletonList(userNodes.get(0)));
        System.out.println("Sent event for match users" + uuId1 + uuId2);
    }

    private Long getRandomUserId(Long userId) {
        UserNode userNode = userNodePersistenceService.findById(userId);
        List<UserNode> userListFromCache = localCache2.get(LOCAL_CACHE_KEY2);
        UserNode get = null;
        int count = 0;
        for (int i = 0; i < userListFromCache.size(); i++) {
            if (userListFromCache.contains(userNode)) {
                count++;
            }
            get = userListFromCache.get(i);
        }
        return Objects.nonNull(get) ? get.getId() : 0L;
    }
}
