package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    public void testAddPiste() {
        // Given
        Piste piste = new Piste();
        piste.setNamePiste("Test Piste");

        // When
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);
        Piste result = pisteServices.addPiste(piste);

        // Then
        assertNotNull(result);
        assertEquals(piste.getNamePiste(), result.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    public void testRetrieveAllPistes() {
        // Given
        List<Piste> pistes = new ArrayList<>();
        pistes.add(new Piste());
        pistes.add(new Piste());

        // When
        when(pisteRepository.findAll()).thenReturn(pistes);
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Then
        assertEquals(2, result.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    public void testRetrievePisteById() {
        // Given
        Long numPiste = 1L;
        Piste piste = new Piste();
        piste.setNumPiste(numPiste);

        // When
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));
        Piste result = pisteServices.retrievePiste(numPiste);

        // Then
        assertEquals(numPiste, result.getNumPiste());
        verify(pisteRepository, times(1)).findById(numPiste);
    }

    @Test
    public void testRemovePiste() {
        // Given
        Long numPiste = 1L;

        // When
        doNothing().when(pisteRepository).deleteById(numPiste);
        pisteServices.removePiste(numPiste);

        // Then
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }

    // Add more test methods as needed...
}
