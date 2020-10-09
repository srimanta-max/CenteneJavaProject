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
@RequestMapping(value="/enrollementDetails")
public class EnrolleeController {

    private EnrolleeService enrolleeService;

    private DependentService dependentService;

    public EnrolleeController(EnrolleeService enrolleeService,
                              DependentService dependentService){
        this.dependentService = dependentService;
        this.enrolleeService = enrolleeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/enrollee/addEnrollee", method=RequestMethod.POST)
    public ResponseEntity<Enrollee> addEnrollee(@RequestBody Enrollee enrollee) {
        log.info("Adding new enrollee");
        Enrollee result = enrolleeService.save(enrollee);
        return new ResponseEntity<>(enrollee,HttpStatus.CREATED);
    }

    @RequestMapping(value="/enrollee/getAllEnrollee", method=RequestMethod.GET)
    public ResponseEntity<List<Enrollee>> getAllEnrolle(){
        log.info("Fetching list of enrolles");
        List<Enrollee> enrolleeList = enrolleeService.getAll();
        return new ResponseEntity<>(enrolleeList,HttpStatus.OK);


    }

    @RequestMapping(value="/enrollee/{enrolleeId}", method=RequestMethod.GET)
    public ResponseEntity<Enrollee> getEnrolleeById(@PathVariable Long enrolleeId) {
        log.info("Fetching Enrollee having id={}", enrolleeId);
         Enrollee enrollee = enrolleeService.get(enrolleeId);
        return new ResponseEntity<>(enrollee,HttpStatus.OK);
    }

    @RequestMapping(value="/enrollee/modifyEnrollee", method=RequestMethod.PUT)
    public ResponseEntity<Enrollee> modifyEnrollee( @RequestBody Enrollee enrollee) {
        log.info("Modifying Enrollee having id={}", enrollee.getId());
         Enrollee result = enrolleeService.update( enrollee);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/enrollee/deleteEnrollee/{enrolleeId}", method=RequestMethod.DELETE)
    public void removeEnrolleById(@PathVariable Long enrolleeId) {
        log.info("Removing Enrollee having enrolleeId={}", enrolleeId);
        enrolleeService.delete(enrolleeId);
    }

    @RequestMapping(value="/dependents/addDependents", method=RequestMethod.POST)
    public ResponseEntity<Enrollee> addDependents(@RequestParam(name="enrolleeId") Long enrolleeId,
                                  @RequestBody List<Dependent> dependents) {
        log.info("Adding Dependents for enrolleeId={}", enrolleeId);
        Enrollee result =  dependentService.add(enrolleeId, dependents);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @RequestMapping(value="/dependents/{enrolleeId}", method=RequestMethod.GET)
    public ResponseEntity<List<Dependent>> getAllDependents(@PathVariable(name="enrolleeId") Long enrolleeId){
        Enrollee enrollee = enrolleeService.get(enrolleeId);
         List<Dependent> dependentList = enrollee.getDependents();
         return new ResponseEntity<>(dependentList,HttpStatus.OK);
    }

    @RequestMapping(value="/dependents/modifyDependent/{enrolleeId}", method=RequestMethod.PUT)
    public ResponseEntity<Dependent> modifyDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                         @RequestBody Dependent dependent) {
        log.info("Modifying dependent for enrolleeId={}, dependentId={}", enrolleeId, dependent.getId());
         Dependent result = dependentService.update(enrolleeId, dependent);
         return new ResponseEntity<>(result,HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/dependents/removeDependent/{enrolleeId}/{dependentId}",
                    method=RequestMethod.DELETE)
    public void removeDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                @PathVariable(name="dependentId") Long dependentId) {
        log.info("Removing dependent for enrolleeId={}, dependentId={}", enrolleeId, dependentId);
        dependentService.remove(enrolleeId, dependentId);
    }
}
