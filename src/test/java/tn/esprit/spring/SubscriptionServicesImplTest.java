package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class SubscriptionServicesImplTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @Test
     void testAddSubscription() {
        // Given
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);

        // When
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        Subscription result = subscriptionServices.addSubscription(subscription);

        // Then
        assertEquals(subscription.getTypeSub(), result.getTypeSub());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
     void testRetrieveSubscriptionById() {
        // Given
        Long numSubscription = 1L;
        Subscription subscription = new Subscription();
        subscription.setNumSub(numSubscription);

        // When
        when(subscriptionRepository.findById(numSubscription)).thenReturn(Optional.of(subscription));
        Subscription result = subscriptionServices.retrieveSubscriptionById(numSubscription);

        // Then
        assertEquals(numSubscription, result.getNumSub());
        verify(subscriptionRepository, times(1)).findById(numSubscription);
    }



   // Test showMonthlyRecurringRevenue for calculation
   @Test
   void testShowMonthlyRecurringRevenue() {
      when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)).thenReturn(100f);
      when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL)).thenReturn(200f);
      when(subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL)).thenReturn(1200f);

      subscriptionServices.showMonthlyRecurringRevenue();

      verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
      verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL);
      verify(subscriptionRepository, times(1)).recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);
   }


    // Add more test methods as needed...

}