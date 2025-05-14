package entities;

public class Palavra {
    private Long id;
    private String texto;
    private Tema tema;

    public Palavra(String texto, Tema tema) {
        this.texto = texto.toUpperCase(); // Armazena o texto em maiúsculas para padronização
        this.tema = tema;
        this.tema = tema;
    }

    // Getters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public Tema getTema() { return tema; }
    public void setId(Long id) { this.id = id; }
}