package com.centene.codchallenge.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name= "enrollee")
public class Enrollee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Boolean activationStatus;

    private String phoneNumber;

    @OneToMany(
        mappedBy = "enrollee",
        cascade = CascadeType.MERGE,
        orphanRemoval = true
    )
    private List<Dependent> dependents = new ArrayList<>();


    public Enrollee addDependent(Dependent dependent) {

        dependents.add(dependent);
        dependent.setEnrollee(this);
        return this;
    }

    public Enrollee addDependents(List<Dependent> dependents) {
       dependents.forEach(this::addDependent);
       return this;
    }

    public Enrollee removeDependent(Dependent dependent){

        dependents.remove(dependent);
        dependent.setEnrollee(null);
        return this;
    }


}