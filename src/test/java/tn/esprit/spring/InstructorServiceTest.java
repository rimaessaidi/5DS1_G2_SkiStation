package tn.esprit.spring;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j

public class InstructorServiceTest {
    @InjectMocks
    InstructorServicesImpl instructorServices;

    @Mock
    IInstructorRepository instructorRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Test
    public void testAddSkier() {
        Instructor instructor=new Instructor();
        Set<Course> courses = new HashSet<>();
        instructor.setNumInstructor(2L);
        instructor.setLastName("j");
        instructor.setFirstName("Lina");
        instructor.setDateOfHire(LocalDate.of(1990, 1, 1));
        instructor.setCourses(courses);

        // Mock the repository's save method
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructor(instructor);

        // Verify that the save method was called once
        verify(instructorRepository, times(1)).save(instructor);

        // Verify that the result is not null
        assertNotNull(result);

        // Verify that the result is the same as the input skier
        assertEquals(instructor, result);
    }




    @Test
    public void testRetrieveAllSkiers() {
        Set<Course> courses = new HashSet<>();
        // Create a list of Skier objects using the parameterized constructor
        List<Instructor>instructorList=new ArrayList<Instructor>();
        instructorList.add(new Instructor(2L,"Lina","Lina", LocalDate.of(2020, 6, 16),courses));
        instructorList.add(new Instructor(3L,"LinaLina","Lina", LocalDate.of(2020, 9, 19),courses));

        // Mock the behavior of the skierRepository to return the list of skiers
        when(instructorRepository.findAll()).thenReturn(instructorList);

        List<Instructor> result = instructorServices.retrieveAllInstructors();

        // Verify that the service method returns the expected result
        assertEquals(instructorList, result);

    }

    @Test
    public void testRetrieveSkier() {
        Set<Course> courses = new HashSet<>();
        Instructor instructor=new Instructor(1L,"Lina","Lina", LocalDate.of(2020, 5, 15),courses);


        // Mock the behavior of the skierRepository to return the sampleSkier when findById is called with skierId
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        // Call the retrieveSkier function
        Instructor retrievedInstructor = instructorServices.retrieveInstructor(1L);

        // Verify that the retrieved skier is the same as the sampleSkier
        assertEquals(instructor, retrievedInstructor);

        // Verify that the retrievedSkier's ID matches the skierId
        assertEquals(1L, retrievedInstructor.getNumInstructor());
    }
}
