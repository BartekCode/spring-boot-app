package com.bartek.restApi.model;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Table(name = "discoveries")
public class Discovery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Discovery's title must be not empty")
    private String title;
    @NotBlank(message = "Discovery's descritpion must be not empty")
    private String description;
    private String url;
    private boolean done;
    private LocalDateTime dateAdded;
    private LocalDateTime updatedOn;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void prePersist(){
        dateAdded = LocalDateTime.now(); //czyli data dodania to aktualna data
    }
    @PreUpdate
    void preUpdate(){
        updatedOn = LocalDateTime.now();
    }

    public void updateFrom(final Discovery source){
        title = source.title;
        description = source.description;
        done = source.done;
    }

}
