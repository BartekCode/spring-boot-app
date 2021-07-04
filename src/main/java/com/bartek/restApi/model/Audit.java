package com.bartek.restApi.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable //klasa osadzalna w innych miejscach
//@MappedSuperclass //klasa bazowa dla encji
public class Audit {

    private LocalDateTime dateAdded;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        dateAdded = LocalDateTime.now(); //czyli data dodania to aktualna data
    }
    @PreUpdate
    void preUpdate(){
        updatedOn = LocalDateTime.now();
    }

}
