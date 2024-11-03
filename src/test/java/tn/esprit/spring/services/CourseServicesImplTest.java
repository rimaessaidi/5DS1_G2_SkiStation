package tn.esprit.spring.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServicesImplTest {

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
    }

    @Test
    public void testRetrieveAllCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();
        assertEquals(1, result.size());
        assertEquals("THEORY", result.get(0).getTypeCourse().toString());
        assertEquals(Support.SKI, result.get(0).getSupport());

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testAddCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.addCourse(course);
        assertEquals(3, result.getLevel());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, result.getTypeCourse());
        assertEquals(200.0f, result.getPrice());

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testUpdateCourse() {
        course.setLevel(4);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseServices.updateCourse(course);
        assertEquals(4, result.getLevel());
        assertEquals(Support.SNOWBOARD, result.getSupport());

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testRetrieveCourse() {
        when(courseRepository.findById(course.getNumCourse())).thenReturn(Optional.of(course));

        Course result = courseServices.retrieveCourse(course.getNumCourse());
        assertEquals(3, result.getLevel());
        assertEquals(TypeCourse.COLLECTIVE_CHILDREN, result.getTypeCourse());

        verify(courseRepository, times(1)).findById(course.getNumCourse());
    }
}
