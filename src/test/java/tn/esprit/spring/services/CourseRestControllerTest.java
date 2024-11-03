package tn.esprit.spring.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.controllers.CourseRestController;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseRestController.class)
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseServices courseServices;

    private Course course;

    @BeforeEach
    public void setup() {
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(3);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(200.0f);
        course.setTimeSlot(2);
        course.setRegistrations(new HashSet<>()); // Mock empty set of registrations
    }

    @Test
    public void testAddCourse() throws Exception {
        when(courseServices.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/course/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"level\":3,\"typeCourse\":\"THEORY\",\"support\":\"ONLINE\",\"price\":200.0,\"timeSlot\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numCourse").value(course.getNumCourse()))
                .andExpect(jsonPath("$.level").value(course.getLevel()))
                .andExpect(jsonPath("$.typeCourse").value(course.getTypeCourse().toString()))
                .andExpect(jsonPath("$.support").value(course.getSupport().toString()));

        verify(courseServices, times(1)).addCourse(any(Course.class));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(course);
        when(courseServices.retrieveAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/course/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(courses.size()))
                .andExpect(jsonPath("$[0].numCourse").value(course.getNumCourse()))
                .andExpect(jsonPath("$[0].level").value(course.getLevel()));

        verify(courseServices, times(1)).retrieveAllCourses();
    }

    @Test
    public void testUpdateCourse() throws Exception {
        course.setLevel(4);
        when(courseServices.updateCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numCourse\":1,\"level\":4,\"typeCourse\":\"COLLECTIVE_ADULT\",\"support\":\"SNOWBOARD\",\"price\":200.0,\"timeSlot\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value(course.getLevel()));

        verify(courseServices, times(1)).updateCourse(any(Course.class));
    }

    @Test
    public void testGetById() throws Exception {
        when(courseServices.retrieveCourse(course.getNumCourse())).thenReturn(course);

        mockMvc.perform(get("/course/get/{id-course}", course.getNumCourse()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numCourse").value(course.getNumCourse()))
                .andExpect(jsonPath("$.level").value(course.getLevel()));

        verify(courseServices, times(1)).retrieveCourse(course.getNumCourse());
    }
}
