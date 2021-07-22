package com.bartek.restApi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name ="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Category's name must be not empty")
    private String name;
    private String description;
//    private boolean done;
//    @Embedded
//    private Audit audit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Discovery> discoveries;
}
