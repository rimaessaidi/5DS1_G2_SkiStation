package tn.esprit.spring.services;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@Slf4j // Adds a logger with Lombok
@RunWith(SpringRunner.class)
public class SkierServicesImplTest {

    @Autowired
    private SkierServicesImpl skierServices;

    @MockBean
    private ISkierRepository skierRepository;

    @MockBean
    private ISubscriptionRepository subscriptionRepository;

    @MockBean
    private IPisteRepository pisteRepository;

    @MockBean
    private ICourseRepository courseRepository;

    @MockBean
    private IRegistrationRepository registrationRepository;

    private Skier skier;
    private Subscription subscription;

    @Before
    public void setUp() {
        skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setCity("Aspen");

        subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());
    }

    @Test
    public void testRetrieveAllSkiers() {
        when(skierRepository.findAll()).thenReturn(Arrays.asList(skier));
        List<Skier> result = skierServices.retrieveAllSkiers();

        log.info("\nTest RetrieveAllSkiers: Retrieved {} skiers", result.size());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(skier.getNumSkier(), result.get(0).getNumSkier());
        verify(skierRepository).findAll();
    }

    @Test
    public void testAddSkier() {
        skier.setSubscription(subscription);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.addSkier(skier);
        log.info("\nTest AddSkier: Added Skier {}", result);
        assertNotNull(result);
        assertEquals(subscription.getEndDate(), skier.getSubscription().getEndDate());
        verify(skierRepository).save(skier);
    }

    @Test
    public void testAssignSkierToSubscription() {
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToSubscription(1L, 1L);
        log.info("\nTest AssignSkierToSubscription: Assigned Skier {} to Subscription {}", skier, subscription);
        assertNotNull(result);
        assertEquals(subscription, result.getSubscription());
        verify(skierRepository).save(skier);
    }
}
