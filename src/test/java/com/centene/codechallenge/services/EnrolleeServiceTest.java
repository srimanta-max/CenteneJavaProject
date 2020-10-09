package com.centene.codechallenge.services;

import com.centene.codchallenge.models.Enrollee;
import com.centene.codchallenge.repositories.EnrolleeRepository;
import com.centene.codchallenge.services.EnrolleeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnrolleeServiceTest {

    @InjectMocks
    private EnrolleeService enrolleeService;

    @Mock
    private EnrolleeRepository enrolleeRepository;

    @Mock
    private Enrollee enrollee;

    @Test
    public void test_save_enrollee(){
        Long id = new Random().nextLong();
        enrolleeService.save(enrollee);
        verify(enrolleeRepository,times(1)).save(enrollee);

    }

    @Test
    public void test_update_enrollee(){
        Long id = new Random().nextLong();
        when(enrollee.getId()).thenReturn(id);
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(enrollee));
        enrolleeService.update(enrollee);
        verify(enrolleeRepository,times(1)).save(enrollee);
    }

    @Test
    public void test_remove_enrollee() {
        Long id = new Random().nextLong();
        when(enrolleeRepository.findById(id)).thenReturn(Optional.of(enrollee));
        enrolleeService.delete(id);
        verify(enrolleeRepository,times(1)).deleteById(id);
    }



}
