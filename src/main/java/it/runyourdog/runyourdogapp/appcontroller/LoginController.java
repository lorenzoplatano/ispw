package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.*;
import it.runyourdog.runyourdogapp.exceptions.DAOException;
import it.runyourdog.runyourdogapp.exceptions.InvalidInputException;
import it.runyourdog.runyourdogapp.exceptions.ProfileRetrievalException;
import it.runyourdog.runyourdogapp.model.dao.DogsitterDao;
import it.runyourdog.runyourdogapp.model.dao.UserDao;
import it.runyourdog.runyourdogapp.model.dao.VeterinarioDao;
import it.runyourdog.runyourdogapp.model.dao.PadroneDao;
import it.runyourdog.runyourdogapp.model.entities.*;
import it.runyourdog.runyourdogapp.utils.Printer;
import javax.security.auth.login.CredentialException;
import java.util.List;

public class LoginController {

    private final UserDao userDao;
    private final PadroneDao padroneDao;
    private final VeterinarioDao veterinarioDao;
    private final DogsitterDao dogsitterDao;

    public LoginController() {
        this.userDao = new UserDao();
        this.padroneDao = new PadroneDao();
        this.veterinarioDao = new VeterinarioDao();
        this.dogsitterDao = new DogsitterDao();
    }

    public UserBean authenticate(LoginBean credentials) throws CredentialException, DAOException, InvalidInputException {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        User user = new User(email, password);

        user = userDao.loginProcedure(user);
        if (user.getRole() == null) {
            throw new CredentialException("Credenziali invalide");
        }

        return new UserBean(user.getUsername(), email, password, user.getRole());
    }

    public ProfiloPadroneBean getPadProfileInfo(UserBean loggedUser) throws ProfileRetrievalException, InvalidInputException {
        try {
            Padrone pad = new Padrone(loggedUser.getEmail(), loggedUser.getPassword());
            pad = padroneDao.padInfo(pad);
            Dog dog = padroneDao.dogInfo(pad);

            String[] datiCane = {
                    dog.getNome(),
                    dog.getSesso(),
                    dog.getRazza(),
                    dog.getMicrochip(),
                    pad.getNome(),
                    pad.getTelefono(),
                    pad.getIndirizzo(),
                    pad.getCitta()
            };

            return new ProfiloPadroneBean(dog.getDataNascita(), dog.getVaccinazioni(), datiCane);

        } catch (DAOException e) {
            Printer.perror(e.getMessage());
            throw new ProfileRetrievalException("Errore nel recupero del profilo del padrone", e);
        }
    }

    public ProfiloVeterinarioBean getVetProfileInfo(UserBean loggedUser) throws ProfileRetrievalException, InvalidInputException {
        try {
            Veterinario vet = new Veterinario(loggedUser.getEmail(), loggedUser.getPassword());
            vet = veterinarioDao.vetInfo(vet);
            List<Orario> orario = veterinarioDao.vetOrari(vet);

            String[] dati = {vet.getNome(), vet.getGenere(), vet.getCitta(), loggedUser.getEmail(), vet.getTelefono()};

            return new ProfiloVeterinarioBean(dati, vet.getEta(), orario, vet.getIndirizzo());

        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del veterinario"+ e.getMessage());
        }
    }

    public ProfiloDogsitterBean getDogProfileInfo(UserBean loggedUser) throws ProfileRetrievalException, InvalidInputException {
        try {
            Dogsitter dogs = new Dogsitter(loggedUser.getEmail(), loggedUser.getPassword());
            dogs = dogsitterDao.dogsInfo(dogs);
            List<Orario> orario = dogsitterDao.dogsOrari(dogs);

            String[] dati = {dogs.getNome(), dogs.getGenere(), dogs.getCitta(), loggedUser.getEmail(), dogs.getTelefono()};

            return new ProfiloDogsitterBean(dati, dogs.getEta(), orario);

        } catch (DAOException e) {
            throw new ProfileRetrievalException("Errore nel recupero del profilo del dogsitter", e);
        }
    }
}
