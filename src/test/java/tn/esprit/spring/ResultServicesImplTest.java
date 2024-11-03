package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Result;
import tn.esprit.spring.repositories.IResultRepository;
import tn.esprit.spring.services.ResultServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResultServicesImplTest {

    @Mock
    private IResultRepository resultRepository;

    @InjectMocks
    private ResultServicesImpl resultServices;

    @Test
    public void testRetrieveAllResults() {
        // Given
        Result result1 = new Result();
        Result result2 = new Result();
        List<Result> resultList = Arrays.asList(result1, result2);

        // When
        when(resultRepository.findAll()).thenReturn(resultList);
        List<Result> results = resultServices.retrieveAllResults();

        // Then
        assertEquals(2, results.size());
        verify(resultRepository, times(1)).findAll();
    }

    @Test
    public void testAddResult() {
        // Given
        Result result = new Result();
        result.setNumResult(1L);

        // When
        when(resultRepository.save(any(Result.class))).thenReturn(result);
        Result addedResult = resultServices.addResult(result);

        // Then
        assertEquals(1L, addedResult.getNumResult());
        verify(resultRepository, times(1)).save(result);
    }

    @Test
    public void testRemoveResult() {
        // Given
        Long numResult = 1L;

        // When
        doNothing().when(resultRepository).deleteById(numResult);
        resultServices.removeResult(numResult);

        // Then
        verify(resultRepository, times(1)).deleteById(numResult);
    }

    @Test
    public void testRetrieveResult() {
        // Given
        Long numResult = 1L;
        Result result = new Result();
        result.setNumResult(numResult);

        // When
        when(resultRepository.findById(numResult)).thenReturn(Optional.of(result));
        Result foundResult = resultServices.retrieveResult(numResult);

        // Then
        assertEquals(numResult, foundResult.getNumResult());
        verify(resultRepository, times(1)).findById(numResult);
    }

    @Test
    public void testRetrieveResultNotFound() {
        // Given
        Long numResult = 1L;

        // When
        when(resultRepository.findById(numResult)).thenReturn(Optional.empty());
        Result foundResult = resultServices.retrieveResult(numResult);

        // Then
        assertNull(foundResult);
        verify(resultRepository, times(1)).findById(numResult);
    }
}
