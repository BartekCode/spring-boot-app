package com.bartek.restApi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name ="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Category's name must be not empty")
    private String name;
    private String description;

    @Embedded
    private Audit audit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Discovery> discoveries;
}
