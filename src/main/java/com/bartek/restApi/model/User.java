package com.bartek.restApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @NotEmpty
     //Jackson’s @JsonIgnore is used to prevent the password field from being serialized into JSON.
    @JsonIgnore
    private String password;
    @Enumerated()
//    @Column(name = "role",columnDefinition = "enum('USER','ADMIN')")
    @Column(columnDefinition = "enum")
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Discovery> discoveries;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Comment> comments;

    public User(@NotEmpty String firstName, @NotEmpty String lastName, @Email String email, @NotEmpty String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
