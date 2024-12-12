package com.loop.service.persistence.jpa.cassandra.repository;

import com.loop.service.persistence.jpa.cassandra.entity.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface PostRepository extends CassandraRepository<Post, String> {
    List<Post> findByUserId(Long userId);
}
