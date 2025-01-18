package com.example.Challenge_LiterAlura.model;

public enum Idiomas {
    ESPANIOL("es", "Español"),
    INGLES("en", "Inglés"),
    PORTUGUES("pt","Portugués"),
    FRANCES("fr","Francés"),
    ITALIANO("it", "Italiano"),
    ALEMAN("de", "Alemán");
    private final String idiomaGutendex;
    private final String idiomaEspanol;
    Idiomas(String idiomaGutendex, String idiomaEspanol) {
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspanol = idiomaEspanol;

    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    @Override
    public String toString() {
        return idiomaEspanol;
    }
}

