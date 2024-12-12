package com.loop.service.persistence.jpa.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNetwork {

    @Id
    private Long id;
    private String userName;
    private String email;
    private String profilePicture;
    private String bio;
    private Long communityId;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private List<UserNetwork> friends;

}
