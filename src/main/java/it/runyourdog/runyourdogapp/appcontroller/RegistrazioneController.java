package it.runyourdog.runyourdogapp.appcontroller;

import it.runyourdog.runyourdogapp.beans.ProfiloPadroneBean;

public class RegistrazioneController {

    private ProfiloPadroneBean profiloPadroneBean;


    public void setProfiloPadroneBean(ProfiloPadroneBean profiloPadroneBean) {
        this.profiloPadroneBean = profiloPadroneBean;
    }


    public ProfiloPadroneBean getProfiloPadroneBean() {
        return this.profiloPadroneBean;
    }
}

