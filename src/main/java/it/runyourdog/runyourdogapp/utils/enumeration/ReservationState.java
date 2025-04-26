package it.runyourdog.runyourdogapp.utils.enumeration;

public enum ReservationState {

    IN_ATTESA(1),
    ACCETTATA(2),
    RIFIUTATA(3),
    CANCELLATA(4);

    private final int id;

    ReservationState(int id) {
        this.id = id;
    }

    public static ReservationState fromInt(int id) {
        for (ReservationState type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {return id;}
}
