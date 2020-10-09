package com.centene.codchallenge.repositories;

import com.centene.codchallenge.models.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {

}
