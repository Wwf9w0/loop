package com.loop.service.service.game;

import com.loop.service.persistence.jpa.neo4j.entity.UserNode;
import com.loop.service.persistence.jpa.neo4j.service.UserNodePersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
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
    private static final Queue<Long> queue1 = new LinkedList<>();
    private static final Queue<UserNode> queue2 = new LinkedList<>();
    private static final Queue<List<UserNode>> queue3 = new LinkedList<>();

    public void randomGame(Long userId, GameType gameType) {
        System.out.println("Game Type : " + gameType);
        if (Objects.equals(gameType, GameType.T)) {

            System.out.println("Do T" + Arrays.toString(T_Type.values()));
        } else if (Objects.equals(gameType, GameType.L)) {
            System.out.println("Do L" + Arrays.toString(L_Type.values()));
        }
        play(userId);
    }

    public void play(Long userId) {
        List<UserNode> recommendUser = localCache2.get(LOCAL_CACHE_KEY2);
        List<UserNode> readyUser = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserNode user = recommendUser.get(i);
            readyUser.add(user);
            int random = new Random().nextInt(10);
            UserNode randomUser = readyUser.get(random);
            List<UserNode> cache = localCache.getOrDefault(LOCAL_CACHE_KEY, new ArrayList<>());
            if (cache.contains(randomUser)) {
                sendEvent(userId, randomUser.getId());
                doGame(userId);
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
            queue1.add(get.getId());
        }
        return Objects.nonNull(get) ? get.getId() : 0L;
    }

    private void doGame(Long userId) {
        Long q = consumerQueue();
        if (q <= 0) {
            throw new RuntimeException("queue null excption! -> q=0 !!!");
        }
        System.out.println("DoGame Player1 = " + userId + " Player2 = " + q);
    }

    private Long consumerQueue() {
        Long q = queue1.peek();
        queue1.poll();
        return getRandomUserId(q);
    }

    private enum GameType {
        T, L;
    }

    private enum T_Type {
        SOCCER,
        BASKETBALL,
        VOLLEYBALL;
    }

    private enum L_Type {
        STRIKE,
        FIGHT;
    }
}
