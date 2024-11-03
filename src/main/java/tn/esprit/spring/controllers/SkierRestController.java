package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.SkierDTO;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.ISkierServices;

import java.util.List;

@Tag(name = "\uD83C\uDFC2 Skier Management")
@RestController
@RequestMapping("/skier")
@RequiredArgsConstructor
public class SkierRestController {

    private final ISkierServices skierServices;

    public Skier convertToEntity(SkierDTO skierDTO) {
        Skier skier = new Skier();
        skier.setFirstName(skierDTO.getFirstName());
        skier.setLastName(skierDTO.getLastName());
        skier.setDateOfBirth(skierDTO.getDateOfBirth());
        skier.setCity(skierDTO.getCity());
        // Set subscription if needed
        return skier;
    }

    @Operation(description = "Add Skier")
    @PostMapping("/add")
    public Skier addSkier(@RequestBody SkierDTO skierDTO) {
        // Convert SkierDTO to Skier entity
        Skier skier = new Skier();
        skier.setNumSkier(skierDTO.getNumSkier());
        skier.setLastName(skierDTO.getLastName());
        skier.setFirstName(skierDTO.getFirstName());
        skier.setDateOfBirth(skierDTO.getDateOfBirth());
        // Set other fields as necessary

        return skierServices.addSkier(skier);
    }


    @Operation(description = "Add Skier And Assign To Course")
    @PostMapping("/addAndAssign/{numCourse}")
    public Skier addSkierAndAssignToCourse(@RequestBody SkierDTO skierDTO,
                                           @PathVariable("numCourse") Long numCourse) {
        // Convert SkierDTO to Skier entity
        Skier skier = new Skier();
        skier.setNumSkier(skierDTO.getNumSkier());
        skier.setFirstName(skierDTO.getFirstName());
        skier.setLastName(skierDTO.getLastName());
        skier.setDateOfBirth(skierDTO.getDateOfBirth());
        skier.setCity(skierDTO.getCity());
        // Set other fields as necessary

        return skierServices.addSkierAndAssignToCourse(skier, numCourse);
    }

    @Operation(description = "Assign Skier To Subscription")
    @PutMapping("/assignToSub/{numSkier}/{numSub}")
    public Skier assignToSubscription(@PathVariable("numSkier")Long numSkier,
                               @PathVariable("numSub") Long numSub){
        return skierServices.assignSkierToSubscription(numSkier, numSub);
    }

    @Operation(description = "Assign Skier To Piste")
    @PutMapping("/assignToPiste/{numSkier}/{numPiste}")
    public Skier assignToPiste(@PathVariable("numSkier")Long numSkier,
                               @PathVariable("numPiste") Long numPiste){
        return skierServices.assignSkierToPiste(numSkier,numPiste);
    }
    @Operation(description = "retrieve Skiers By Subscription Type")
    @GetMapping("/getSkiersBySubscription")
    public List<Skier> retrieveSkiersBySubscriptionType(TypeSubscription typeSubscription) {
        return skierServices.retrieveSkiersBySubscriptionType(typeSubscription);
    }
    @Operation(description = "Retrieve Skier by Id")
    @GetMapping("/get/{id-skier}")
    public Skier getById(@PathVariable("id-skier") Long numSkier){
        return skierServices.retrieveSkier(numSkier);
    }

    @Operation(description = "Delete Skier by Id")
    @DeleteMapping("/delete/{id-skier}")
    public void deleteById(@PathVariable("id-skier") Long numSkier){
        skierServices.removeSkier(numSkier);
    }

    @Operation(description = "Retrieve all Skiers")
    @GetMapping("/all")
    public List<Skier> getAllSkiers(){
        return skierServices.retrieveAllSkiers();
    }

}
