package com.bartek.restApi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Discovery's title must be not empty")
    private String title;
    @NotBlank(message = "Discovery's descritpion must be not empty")
    private String description;
    private String url;
    private boolean done;
    private LocalDateTime dateAdded;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    private Audit audit = new Audit();


    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description, String url, LocalDateTime dateAdded) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.dateAdded = dateAdded;
    }

    public Discovery(@NotBlank(message = "Discovery's title must be not empty") String title, @NotBlank(message = "Discovery's descritpion must be not empty") String description, String url, boolean done) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.done = done;
    }

    public void updateFrom(final Discovery source){
        title = source.title;
        description = source.description;
        done = source.done;
        category = source.category;
    }

}
