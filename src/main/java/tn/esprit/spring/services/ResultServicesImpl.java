package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Result;
import tn.esprit.spring.repositories.IResultRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ResultServicesImpl implements  IResultServices{

    private IResultRepository resultRepository;

    @Override
    public List<Result> retrieveAllResults() {
        log.info("Retrieving all results!");
        try{
            List<Result> results = resultRepository.findAll();
            log.info("Successfully retrieved results.");
            return results;
        }
        catch(Exception e){
            log.error("Error retrieving result", e);
            return null;
        }
    }

    @Override
    public Result addResult(Result result) {
        log.info("Adding new result: {}", result);
        try{
            Result addedResult = resultRepository.save(result);
            log.info("Successfully added result with ID: {}", addedResult.getNumSub());
            return addedResult;
        }
        catch(Exception e){
            log.error("Error adding result", e);
            return null;
        }
    }

    @Override
    public void removeResult(Long numResult) {
        log.info("Deleting result with id: {}", numResult);
        try{
            resultRepository.deleteById(numResult);
            log.info("Successfully deleted result with ID: {}", numResult);
        }
        catch(Exception e){
            log.error("Error deleting result", e);
        }
    }

    @Override
    public Result retrieveResult(Long numResult) {
        log.info("Retrieving result with id: {}", numResult);
        try{
            Result result = resultRepository.findById(numResult).orElse(null);
            if(result == null){
                log.warn("No such result, null returned.");
            }
            log.info("Successfully retrieved result with ID: {}", numResult);
            return result;
        }
        catch(Exception e){
            log.error("Error retrieving result", e);
            return null;
        }
        
    }
}
