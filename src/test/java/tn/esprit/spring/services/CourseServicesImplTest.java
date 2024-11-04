package tn.esprit.spring.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServicesImplTest {

    private static final Logger logger = LogManager.getLogger(CourseServicesImplTest.class);

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    private Course course;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(3);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(200.0f);
        course.setTimeSlot(2);
        logger.info("Setup completed for CourseServicesImplTest");
    }

    @Test
    void testRetrieveAllCourses() {
        logger.info("Starting testRetrieveAllCourses");
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();
        assertEquals(1, result.size());
        assertEquals("COLLECTIVE_CHILDREN", result.get(0).getTypeCourse().toString());
        assertEquals(Support.SKI, result.get(0).getSupport());

        verify(courseRepository, times(1)).findAll();
        logger.info("Completed testRetrieveAllCourses successfully");
    }

    @Test
    void testAddCourse() {
        logger.info("Starting testAddCourse");
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.addCourse(course);
        assertEquals(3, result.getLevel());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, result.getTypeCourse());
        assertEquals(200.0f, result.getPrice());

        verify(courseRepository, times(1)).save(any(Course.class));
        logger.info("Completed testAddCourse successfully");
    }

    @Test
    void testUpdateCourse() {
        logger.info("Starting testUpdateCourse");
        course.setLevel(4);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.updateCourse(course);
        assertEquals(4, result.getLevel());
        assertEquals(Support.SKI, result.getSupport());

        verify(courseRepository, times(1)).save(any(Course.class));
        logger.info("Completed testUpdateCourse successfully");
    }

    @Test
    void testRetrieveCourse() {
        logger.info("Starting testRetrieveCourse");
        when(courseRepository.findById(course.getNumCourse())).thenReturn(Optional.of(course));

        Course result = courseServices.retrieveCourse(course.getNumCourse());
        assertEquals(3, result.getLevel());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, result.getTypeCourse());

        verify(courseRepository, times(1)).findById(course.getNumCourse());
        logger.info("Completed testRetrieveCourse successfully");
    }
}
