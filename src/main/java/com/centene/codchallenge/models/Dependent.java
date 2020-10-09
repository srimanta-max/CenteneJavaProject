package com.centene.codchallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dependent")
public class Dependent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateOfBirth;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Enrollee enrollee;


}
