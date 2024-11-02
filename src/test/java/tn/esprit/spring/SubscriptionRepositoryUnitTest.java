package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SubscriptionRepositoryUnitTest {

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    private Subscription monthlySubscription;
    private Subscription annualSubscription;

    @BeforeEach
    void setUp() {
        monthlySubscription = new Subscription(null, LocalDate.now(), LocalDate.now().plusMonths(1), 100.0f, TypeSubscription.MONTHLY);
        annualSubscription = new Subscription(null, LocalDate.now(), LocalDate.now().plusYears(1), 1000.0f, TypeSubscription.ANNUAL);

        subscriptionRepository.save(monthlySubscription);
        subscriptionRepository.save(annualSubscription);
    }

    @Test
    void testFindByTypeSubOrderByStartDateAsc() {
        Set<Subscription> result = subscriptionRepository.findByTypeSubOrderByStartDateAsc(TypeSubscription.MONTHLY);

        assertThat(result).isNotEmpty();
        assertThat(result.iterator().next().getTypeSub()).isEqualTo(TypeSubscription.MONTHLY);
    }

    @Test
    void testGetSubscriptionsByStartDateBetween() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusMonths(2);

        List<Subscription> result = subscriptionRepository.getSubscriptionsByStartDateBetween(start, end);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2); // Expecting 2 subscriptions in the range
    }

    @Test
    void testFindDistinctOrderByEndDateAsc() {
        List<Subscription> result = subscriptionRepository.findDistinctOrderByEndDateAsc();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEndDate()).isBeforeOrEqualTo(result.get(result.size() - 1).getEndDate());
    }

    @Test
    void testRecurringRevenueByTypeSubEquals() {
        Float revenueMonthly = subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY);
        Float revenueAnnual = subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL);

        assertThat(revenueMonthly).isNotNull();
        assertThat(revenueMonthly).isEqualTo(100.0f);

        assertThat(revenueAnnual).isNotNull();
        assertThat(revenueAnnual).isEqualTo(1000.0f);
    }
}

