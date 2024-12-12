package com.loop.service.persistence.jpa.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDocument {

    private String userId;
    private String text;
    private LocalDateTime createdAt;
    private List<String> replies = new ArrayList<>();
}
