package tn.esprit.spring.services;

import tn.esprit.spring.entities.Result;

import java.util.List;

public interface IResultServices {

    List<Result> retrieveAllResults();

    Result addResult(Piste  piste);

    void removeResult (Long numResult);

    Result retrieveResult (Long numResult);
}
