package com.centene.codchallenge.controllers;

import com.centene.codchallenge.models.Dependent;
import com.centene.codchallenge.services.DependentService;
import com.centene.codchallenge.services.EnrolleeService;
import com.centene.codchallenge.models.Enrollee;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/enrollementDetails/v1/enrolles")
public class EnrolleeController {

    private EnrolleeService enrolleeService;

    private DependentService dependentService;

    public EnrolleeController(EnrolleeService enrolleeService,
                              DependentService dependentService){
        this.dependentService = dependentService;
        this.enrolleeService = enrolleeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/enrollee", method=RequestMethod.POST)
    public ResponseEntity<Enrollee> addEnrollee(@RequestBody Enrollee enrollee) {
        log.info("Adding new enrollee");
        Enrollee result = enrolleeService.save(enrollee);
        return new ResponseEntity<>(enrollee,HttpStatus.CREATED);
    }

    @RequestMapping( method=RequestMethod.GET)
    public ResponseEntity<List<Enrollee>> getAllEnrolles(){
        log.info("Fetching list of enrolles");
        List<Enrollee> enrolleeList = enrolleeService.getAll();
        return new ResponseEntity<>(enrolleeList,HttpStatus.OK);


    }

    @RequestMapping(value="/enrollee/{id}", method=RequestMethod.GET)
    public ResponseEntity<Enrollee> getEnrolleeById(@PathVariable Long id) {
        log.info("Fetching Enrollee having id={}", id);
         Enrollee enrollee = enrolleeService.get(id);
        return new ResponseEntity<>(enrollee,HttpStatus.OK);
    }

    @RequestMapping(value="/enrollee", method=RequestMethod.PUT)
    public ResponseEntity<Enrollee> modifyEnrollee( @RequestBody Enrollee enrollee) {
        log.info("Modifying Enrollee having id={}", enrollee.getId());
         Enrollee result = enrolleeService.update( enrollee);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/enrollee/{id}", method=RequestMethod.DELETE)
    public void removeEnrolleById(@PathVariable Long id) {
        log.info("Removing Enrollee having id={}", id);
        enrolleeService.delete(id);
    }

    @RequestMapping(value="/{enrolleeId}/dependents", method=RequestMethod.POST)
    public ResponseEntity<Enrollee> addDependents(@PathVariable Long enrolleeId,
                                  @RequestBody List<Dependent> dependents) {
        log.info("Adding Dependents for enrolleeId={}", enrolleeId);
        Enrollee result =  dependentService.add(enrolleeId, dependents);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @RequestMapping(value="/{enrolleeId}/dependents", method=RequestMethod.GET)
    public ResponseEntity<List<Dependent>> getAllDependents(@PathVariable(name="enrolleeId") Long enrolleeId){
        Enrollee enrollee = enrolleeService.get(enrolleeId);
         List<Dependent> dependentList = enrollee.getDependents();
         return new ResponseEntity<>(dependentList,HttpStatus.OK);
    }

    @RequestMapping(value="/{enrolleeId}/dependents/dependent", method=RequestMethod.PUT)
    public ResponseEntity<Dependent> modifyDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                         @RequestBody Dependent dependent) {
        log.info("Modifying dependent for enrolleeId={}, dependentId={}", enrolleeId, dependent.getId());
         Dependent result = dependentService.update(enrolleeId, dependent);
         return new ResponseEntity<>(result,HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/{enrolleId}/dependents/dependent/{dependentId}",
                    method=RequestMethod.DELETE)
    public void removeDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                @PathVariable(name="dependentId") Long dependentId) {
        log.info("Removing dependent for enrolleeId={}, dependentId={}", enrolleeId, dependentId);
        dependentService.remove(enrolleeId, dependentId);
    }
}
