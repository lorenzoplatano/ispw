package it.runyourdog.runyourdogapp.utils.enumeration;

public enum Role {

    PADRONE(1),
    VETERINARIO(2),
    DOGSITTER(3);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role fromInt(int id) {
        for (Role type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
