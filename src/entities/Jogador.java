package entities;

public class Jogador {
    private Long id;
    private String nome;
    private int pontuacaoTotal;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacaoTotal = 0;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public int getPontuacaoTotal() { return pontuacaoTotal; }
    public void adicionarPontos(int pontos) { pontuacaoTotal += pontos; }
}