package com.centene.codchallenge.services;

import com.centene.codchallenge.exceptions.ServiceException;
import com.centene.codchallenge.models.Dependent;
import com.centene.codchallenge.models.Enrollee;
import com.centene.codchallenge.repositories.EnrolleeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class EnrolleeService {


    private EnrolleeRepository enrolleeRepository;

    public EnrolleeService(EnrolleeRepository enrolleeRepository) {
        this.enrolleeRepository = enrolleeRepository;
    }

    public Enrollee get(Long id) throws ServiceException {
        Optional<Enrollee> enrolleeOptional = enrolleeRepository.findById(id);
        if(enrolleeOptional.isPresent()){
            return enrolleeOptional.get();
        } else {
            log.debug("Resource not available");
            throw new ServiceException("Resource not available");
        }
    }

    public List<Enrollee> getAll()  {
        List<Enrollee> enrolleList
                     = enrolleeRepository.findAll();
        return enrolleList;
    }

    public Enrollee save(Enrollee enrollee)  throws ServiceException {
        if(enrollee.getId() != null){
            Optional<Enrollee> savedEnrolleeOptional = enrolleeRepository.findById(enrollee.getId());
            if(savedEnrolleeOptional.isPresent()){

                log.info("cannot create enrollee");

                throw new ServiceException("Cannot create enrolle");
            } else {
                for (Dependent dependent : enrollee.getDependents()) {
                    dependent.setEnrollee(enrollee);
                }
                return enrolleeRepository.save(enrollee);

            }
        } else {
            for (Dependent dependent : enrollee.getDependents()) {
                dependent.setEnrollee(enrollee);
            }
            return enrolleeRepository.save(enrollee);
        }
    }

    public Enrollee update( Enrollee enrollee)  throws ServiceException {

        if(enrollee.getId() != null) {
            Optional<Enrollee> savedEnrolleeOptional = enrolleeRepository.findById(enrollee.getId());
            if (savedEnrolleeOptional.isPresent()) {

                Enrollee savedEnrollee = savedEnrolleeOptional.get();

                savedEnrollee.setName(enrollee.getName());
                savedEnrollee.setDateOfBirth(enrollee.getDateOfBirth());
                savedEnrollee.setPhoneNumber(enrollee.getPhoneNumber());
                savedEnrollee.setActivationStatus(enrollee.getActivationStatus());
                savedEnrollee.getDependents().clear();
                savedEnrollee.getDependents().addAll(enrollee.getDependents());
                log.debug("Updating enrollee with id={}", enrollee.getId());

                return enrolleeRepository.save(savedEnrollee);
            } else {
                throw new ServiceException("Resource not available");
            }

        }
        else {
            log.debug("Update request missing id");
            throw new ServiceException("Update request missing id");
        }

    }

    public void delete(Long id)  throws ServiceException {
        if(id != null ){
            Optional<Enrollee> enrolleeOptional  = enrolleeRepository.findById(id);
            if(enrolleeOptional.isPresent()){

                log.debug("Deleting enrollee with id={}", id);

                enrolleeRepository.deleteById(id);
            } else {
                log.debug("Resource not available");
                throw new ServiceException("Resource not available");
            }
        } else {
            log.debug("Delete request missing id");
            throw new ServiceException("Delete request missing id");
        }

    }

}