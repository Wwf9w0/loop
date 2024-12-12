package com.loop.service.persistence.jpa.cassandra.repository;

import com.loop.service.persistence.jpa.cassandra.entity.PostDocument;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface PostDocumentRepository extends CassandraRepository<PostDocument, String> {
    List<PostDocument> findByUserId(Long userId);
}
