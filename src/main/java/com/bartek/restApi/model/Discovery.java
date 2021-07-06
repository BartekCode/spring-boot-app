package com.bartek.restApi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    private Audit audit = new Audit();


    public void updateFrom(final Discovery source){
        title = source.title;
        description = source.description;
        done = source.done;
        category = source.category;
    }

}
