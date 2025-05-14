package entities;

public class Jogador {
    private Long id;
    private String nome;
    private int pontuacaoTotal;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacaoTotal = 0;
    }

    public Jogador(String nome, int pontuacaoTotal) {
        this.nome = nome;
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacaoTotal += pontos;
    }
}