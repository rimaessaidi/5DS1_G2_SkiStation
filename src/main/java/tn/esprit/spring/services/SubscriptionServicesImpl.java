package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionServicesImpl implements ISubscriptionServices{

    private ISubscriptionRepository subscriptionRepository;

    private ISkierRepository skierRepository;

    @Override
    public Subscription addSubscription(Subscription subscription) {
        log.info("Adding new subscription: {}", subscription);

        try {
            switch (subscription.getTypeSub()) {
                case ANNUAL:
                    subscription.setEndDate(subscription.getStartDate().plusYears(1));
                    break;
                case SEMESTRIEL:
                    subscription.setEndDate(subscription.getStartDate().plusMonths(6));
                    break;
                case MONTHLY:
                    subscription.setEndDate(subscription.getStartDate().plusMonths(1));
                    break;
                default:
                    log.warn("Unrecognized subscription type: {}", subscription.getTypeSub());
            }

            Subscription savedSubscription = subscriptionRepository.save(subscription);
            log.info("Successfully added subscription with ID: {}", savedSubscription.getNumSub());
            return savedSubscription;

        } catch (Exception e) {
            log.error("Error adding subscription", e);
            return null;
        }
    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        log.info("Updating subscription: {}", subscription);
        try {
            Subscription updatedSubscription = subscriptionRepository.save(subscription);
            log.info("Successfully updated subscription with ID: {}", updatedSubscription.getNumSub());
            return updatedSubscription;

        } catch (Exception e) {
            log.error("Error updating subscription", e);
            return null;
        }
    }

    @Override
    public Subscription retrieveSubscriptionById(Long numSubscription) {
        log.debug("Retrieving subscription by ID: {}", numSubscription);
        return subscriptionRepository.findById(numSubscription).orElseGet(() -> {
            log.warn("No subscription found with ID: {}", numSubscription);
            return null;
        });
    }

    @Override
    public Set<Subscription> getSubscriptionByType(TypeSubscription type) {
        log.info("Retrieving subscriptions of type: {}", type);
        return subscriptionRepository.findByTypeSubOrderByStartDateAsc(type);
    }

    @Override
    public List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate) {
        log.info("Retrieving subscriptions between dates: {} and {}", startDate, endDate);
        return subscriptionRepository.getSubscriptionsByStartDateBetween(startDate, endDate);
    }

    @Override
    @Scheduled(cron = "*/30 * * * * *")
    public void retrieveSubscriptions() {
        log.info("Retrieving all subscriptions at scheduled interval");
        subscriptionRepository.findDistinctOrderByEndDateAsc().forEach(sub -> {
            Skier aSkier = skierRepository.findBySubscription(sub);
            log.info("Subscription ID: {}, End Date: {}, Skier: {} {}",
                    sub.getNumSub(), sub.getEndDate(),
                    aSkier.getFirstName(), aSkier.getLastName());
        });
    }

    // @Scheduled(cron = "* 0 9 1 * *") /* Cron expression to run a job every month at 9am */
    @Scheduled(cron = "*/30 * * * * *") /* Cron expression to run a job every 30 secondes */
    public void showMonthlyRecurringRevenue() {
        log.info("Calculating monthly recurring revenue");
        try {
            Float revenue = subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.MONTHLY)
                    + subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.SEMESTRIEL) / 6
                    + subscriptionRepository.recurringRevenueByTypeSubEquals(TypeSubscription.ANNUAL) / 12;
            log.info("Monthly Revenue = {}", revenue);
        } catch (Exception e) {
            log.error("Error calculating monthly revenue", e);
        }
    }
}
