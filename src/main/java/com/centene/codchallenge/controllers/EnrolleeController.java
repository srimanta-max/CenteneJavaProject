package com.centene.codchallenge.controllers;

import com.centene.codchallenge.models.Dependent;
import com.centene.codchallenge.services.DependentService;
import com.centene.codchallenge.services.EnrolleeService;
import com.centene.codchallenge.models.Enrollee;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/enrolle-details")
public class EnrolleeController {

    private EnrolleeService enrolleeService;

    private DependentService dependentService;

    public EnrolleeController(EnrolleeService enrolleeService,
                              DependentService dependentService){
        this.dependentService = dependentService;
        this.enrolleeService = enrolleeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/add-enrollee", method=RequestMethod.POST)
    public Enrollee addEnrollee(@RequestBody Enrollee enrollee) {
        log.info("Adding new enrollee");
        return enrolleeService.save(enrollee);
    }

    @RequestMapping(value="/get-all-enrollee", method=RequestMethod.GET)
    public List<Enrollee> getAllEnrolle(){
        log.info("Fetching list of enrolles");
        return enrolleeService.getAll();


    }

    @RequestMapping(value="/{enrolleeId}", method=RequestMethod.GET)
    public Enrollee getEnrolleeById(@PathVariable Long enrolleeId) {
        log.info("Fetching Enrollee having id={}", enrolleeId);
        return enrolleeService.get(enrolleeId);
    }

    @RequestMapping(value="/modify-enrollee", method=RequestMethod.PUT)
    public Enrollee modifyEnrollee( @RequestBody Enrollee enrollee) {
        log.info("Modifying Enrollee having id={}", enrollee.getId());
        return enrolleeService.update( enrollee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/delete-enrollee/{enrolleeId}", method=RequestMethod.DELETE)
    public void removeEnrolleById(@PathVariable Long enrolleeId) {
        log.info("Removing Enrollee having enrolleeId={}", enrolleeId);
        enrolleeService.delete(enrolleeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/add-dependents", method=RequestMethod.POST)
    public Enrollee addDependents(@RequestParam(name="enrolleeId") Long enrolleeId,
                                  @RequestBody List<Dependent> dependents) {
        log.info("Adding Dependents for enrolleeId={}", enrolleeId);
        return dependentService.add(enrolleeId, dependents);
    }

    @RequestMapping(value="/dependents/{enrolleeId}", method=RequestMethod.GET)
    public List<Dependent> getAllDependents(@PathVariable(name="enrolleeId") Long enrolleeId){
        Enrollee enrollee = enrolleeService.get(enrolleeId);
        return enrollee.getDependents();
    }

    @RequestMapping(value="/dependents/modify-dependent/{enrolleeId}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Dependent modifyDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                         @RequestBody Dependent dependent) {
        log.info("Modifying dependent for enrolleeId={}, dependentId={}", enrolleeId, dependent.getId());
        return dependentService.update(enrolleeId, dependent);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/remove-dependent/{enrolleeId}/{dependentId}",
                    method=RequestMethod.DELETE)
    public void removeDependent(@PathVariable(name="enrolleeId") Long enrolleeId,
                                    @PathVariable(name="dependentId") Long dependentId) {
        log.info("Removing dependent for enrolleeId={}, dependentId={}", enrolleeId, dependentId);
        dependentService.remove(enrolleeId, dependentId);
    }
}
