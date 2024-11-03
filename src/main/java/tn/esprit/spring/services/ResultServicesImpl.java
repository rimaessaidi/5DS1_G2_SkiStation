package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Result;
import tn.esprit.spring.repositories.IResultRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class ResultServicesImpl implements  IResultServices{

    private IResultRepository resultRepository;

    @Override
    public List<Result> retrieveAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public Result addResult(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public void removeResult(Long numResult) {
        resultRepository.deleteById(numPiste);
    }

    @Override
    public Piste retrieveResult(Long numResult) {
        return resultRepository.findById(numPiste).orElse(null);
    }
}
