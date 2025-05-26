package it.runyourdog.runyourdogapp.pattern.observer;

import it.runyourdog.runyourdogapp.beans.PrenotazioneBean;
import it.runyourdog.runyourdogapp.utils.enumeration.ReservationState;


public interface ReservationStateObserver {

    void onStateChanged(PrenotazioneBean pren, ReservationState oldState, ReservationState newState);
}