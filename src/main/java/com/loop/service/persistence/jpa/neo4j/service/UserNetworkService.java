package com.loop.service.persistence.jpa.neo4j.service;

import com.loop.service.persistence.jpa.mysql.entity.UserEntity;
import com.loop.service.persistence.jpa.neo4j.repository.UserNetworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNetworkService {

    private final UserNetworkRepository userNetworkRepository;

    public void network(UserEntity user) {
    }
}
