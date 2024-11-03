package tn.esprit.spring.services;
import org.junit.*;
import org.junit.runner.RunWith;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.slf4j.Slf4j;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest // Load the full application context
@RunWith(SpringRunner.class) // Use SpringRunner for Spring Boot tests in JUnit 4
@Slf4j // Adds a logger with Lombok
public class CourseServicesImplTest {

    @Autowired
    private CourseServicesImpl courseServices; // Use @Autowired instead of @InjectMocks

    @MockBean
    private ICourseRepository courseRepository; // MockBean for dependency

    private Course course;
    private Course course2;

    @Before
    public void setUp() {
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(1);
        course.setPrice(100.0f);
        // Set other properties as needed
        course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(3);
        course2.setPrice(250.0f);
    }

    @Test
    public void testRetrieveAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course2);
        courses.add(course);
        courses.add(course2);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();
        // Use streams to format the output for better readability
        String formattedCourses = result.stream()
                .map(c -> String.format("%nCourse ID: %d, Level: %d, Price: %.2f ",
                        c.getNumCourse(), c.getLevel(), c.getPrice()))
                .collect(Collectors.joining());

        log.info("\n Retrieved courses: {}", formattedCourses);
    }

    @Test
    public void testAddCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.addCourse(course);
        assertNotNull(result);
        assertEquals(course.getNumCourse(), result.getNumCourse());
        log.info("\n Added course: {}", result);
    }

    @Test
    public void testUpdateCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.updateCourse(course);
        assertNotNull(result);
        log.info("\n Updated course: {}", result);
    }

    @Test
    public void testRetrieveCourse() {
        when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course));

        Course result = courseServices.retrieveCourse(1L);
        assertNotNull(result);
        assertEquals(course.getNumCourse(), result.getNumCourse());
        log.info("\n Retrieved course: {}", result);
    }
}