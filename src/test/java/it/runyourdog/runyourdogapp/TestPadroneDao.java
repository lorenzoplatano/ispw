package it.runyourdog.runyourdogapp;

import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.model.dao.PadroneDaoMySQL;
import it.runyourdog.runyourdogapp.model.entities.Prenotazione;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ONORATO DANIELE 0310075
class TestPadroneDao {

    @Test
    void findVetTest_withNullPrenotazione() {
        PadroneDaoMySQL padroneDao = new PadroneDaoMySQL();
        Assertions.assertThrows(
                DAOException.class,
                () -> padroneDao.findVet(null),
                "Deve lanciare DAOException se la prenotazione è null"
        );
    }

    @Test
    void findVetTest_withIncompletePrenotazione() {
        PadroneDaoMySQL padroneDao = new PadroneDaoMySQL();
        Prenotazione pren = new Prenotazione(1);

        Assertions.assertThrows(
                DAOException.class,
                () -> padroneDao.findVet(pren),
                "Deve lanciare DAOException se la prenotazione è incompleta"
        );
    }

    @Test
    void findDogsitterTest_withNullPrenotazione() {
        PadroneDaoMySQL padroneDao = new PadroneDaoMySQL();
        Assertions.assertThrows(
                DAOException.class,
                () -> padroneDao.findDogsitter(null),
                "Deve lanciare DAOException se la prenotazione è null"
        );
    }

    @Test
    void findDogsitterTest_withIncompletePrenotazione() {
        PadroneDaoMySQL padroneDao = new PadroneDaoMySQL();
        Prenotazione pren = new Prenotazione(1);

        Assertions.assertThrows(
                DAOException.class,
                () -> padroneDao.findDogsitter(pren),
                "Deve lanciare DAOException se la prenotazione è incompleta"
        );
    }
}