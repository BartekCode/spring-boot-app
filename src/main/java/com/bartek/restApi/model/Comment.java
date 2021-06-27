package com.bartek.restApi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDateTime dateAdded;
    @NotBlank(message = "Comments's descritpion must be not empty")
    private String description;
    @ManyToOne
    @JoinColumn(name = "discovery_id")
    private Discovery discovery;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
