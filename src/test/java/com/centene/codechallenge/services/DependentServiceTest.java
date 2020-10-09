package com.centene.codechallenge.services;

import com.centene.codchallenge.exceptions.ServiceException;
import com.centene.codchallenge.models.Dependent;
import com.centene.codchallenge.models.Enrollee;
import com.centene.codchallenge.repositories.DependentRepository;
import com.centene.codchallenge.repositories.EnrolleeRepository;
import com.centene.codchallenge.services.DependentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DependentServiceTest {

    @Mock
    private DependentRepository dependentRepository;

    @Mock
    private EnrolleeRepository enrolleeRepository;

    @InjectMocks
    private DependentService dependentService;


    @Mock
    private Enrollee enrollee;


    @Mock
    private Dependent dependent;



    @Test
    public void test_dependent_addition() {
        Long id = new Random().nextLong();
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(enrollee));

        dependentService.add(id,anyList());
        verify(enrollee,times(1)).addDependents(any());

    }

    @Test(expected = ServiceException.class)
    public void test_dependent_removal_exception() {
        Long id1 = new Random().nextLong();
        Long id2 = new Random().nextLong();

        when(enrolleeRepository.findById(id1)).thenReturn(Optional.of(enrollee));
        when(enrollee.getDependents()).thenReturn(List.of(dependent));

        dependentService.remove(id1,id2);
    }

    @Test
    public void test_dependent_update(){
        Long id1 = new Random().nextLong();

        when(enrolleeRepository.findById(id1)).thenReturn(Optional.of(enrollee));
        when(dependentRepository.findById(anyLong())).thenReturn(Optional.of(dependent));
        when(dependentRepository.save(any())).thenReturn(dependent);

        Dependent result = dependentService.update(id1,dependent);
        verify(dependent,times(1)).setName(any());
    }
}
