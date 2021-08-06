package com.bartek.restApi.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Table(name = "discoveries")
public class Discovery{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Discovery's title must be not empty")
    @JsonProperty("title")
    private String title;
    @NotBlank(message = "Discovery's descritpion must be not empty")
    @JsonProperty("description")
    private String description;
    private String url;
    private boolean done;
    private LocalDateTime dateAdd;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    private Audit audit = new Audit();

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "discovery")
    private List<Comment> comments;


    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description, String url, LocalDateTime dateAdd) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.dateAdd = dateAdd;
    }

    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description, String url, boolean done) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.done = done;
    }

    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description) {
        this.title = title;
        this.description = description;
    }

    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description, LocalDateTime dateAdd) {
        this.title = title;
        this.description = description;
        this.dateAdd = dateAdd;
    }

    public void updateFrom(final Discovery source){
        title = source.title;
        description = source.description;
        done = source.done;
        category = source.category;
    }

}
