package com.loop.service.persistence.jpa.cassandra.entity;

import com.loop.service.persistence.jpa.cassandra.entity.Comment;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @PrimaryKey
    private String id;

    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private List<String> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<String> photos = new ArrayList<>();
    private String theme;
}
