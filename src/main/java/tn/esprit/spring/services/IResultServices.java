package tn.esprit.spring.services;

import tn.esprit.spring.entities.Result;

import java.util.List;

public interface IResultServices {

    List<Result> retrieveAllResults();

    Result addResult(Result  result);

    void removeResult (Long numResult);

    Result retrieveResult (Long numResult);
}
