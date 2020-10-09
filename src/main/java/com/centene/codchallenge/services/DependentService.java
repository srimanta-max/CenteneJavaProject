package com.centene.codchallenge.services;

import com.centene.codchallenge.exceptions.ServiceException;
import com.centene.codchallenge.models.Dependent;
import com.centene.codchallenge.models.Enrollee;
import com.centene.codchallenge.repositories.DependentRepository;
import java.util.List;
import java.util.Optional;

import com.centene.codchallenge.repositories.EnrolleeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DependentService {

    private  DependentRepository dependentRepository;

    private EnrolleeRepository enrolleeRepository;

    public DependentService( DependentRepository dependentRepository,
                            EnrolleeRepository enrolleeRepository){
        this.enrolleeRepository = enrolleeRepository;
        this.dependentRepository = dependentRepository;
    }

    public Enrollee add(Long enrolleId, List<Dependent> dependents) {
           Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(enrolleId);
           if(enrolleeOptional.isPresent()){
               Enrollee parentEnrollee = enrolleeOptional.get();
               parentEnrollee.addDependents(dependents);
               dependentRepository.saveAll(dependents);
               log.debug("Added dependents with enrolleId={}", enrolleId);
               return parentEnrollee;
           } else {
               log.debug("Invalid enrollee ID");
               throw new ServiceException("Invalid enrollee ID");
           }

    }

    public Dependent update(Long enrolleeId, Dependent dependent) throws ServiceException {
        if(enrolleeId != null){
            Optional<Dependent> dependentFromDb = dependentRepository.findById(dependent.getId());
            Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(enrolleeId);

            if(dependentFromDb.isPresent() ){
                Dependent dbDependent = dependentFromDb.get();
                dbDependent.setDateOfBirth(dependent.getDateOfBirth());
                dbDependent.setEnrollee(enrolleeOptional.get());
                dbDependent.setName(dependent.getName());

                log.debug("Updating  dependent for enrolleeId={}", enrolleeId);

                return dependentRepository.save(dbDependent);
            } else {
                throw new ServiceException("Dependent not present");
            }
        } else {
            throw new ServiceException("Enrollee Id not provided");
        }

    }

    public void remove( Long enrolleId, Long dependentId) throws ServiceException {

        Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(enrolleId);
        if(enrolleeOptional.isPresent()) {
            Enrollee parentEnrollee = enrolleeOptional.get();
            List<Dependent> dependentList = parentEnrollee.getDependents();
            Dependent dependent = dependentList.stream()
                    .filter(a -> a.getId().equals(dependentId))
                    .findFirst()
                    .orElseThrow(() -> new ServiceException("Dependent not present with id provided"));
            parentEnrollee.removeDependent(dependent);
            enrolleeRepository.save(parentEnrollee);
            log.debug("Deleted dependent with id={}", dependentId);
        } else {
            log.debug("Invalid enrolleeId");
            throw new ServiceException("Invalid enrolleeId");
        }



        }



}
