package it.runyourdog.runyourdogapp.utils.enumeration;

public enum ReservationType {

    VETERINARIO(1),
    DOGSITTER(2);

    private final int id;

    ReservationType(int id) {
        this.id = id;
    }

    public static ReservationType fromInt(int id) {
        for (ReservationType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {return id;}
}
