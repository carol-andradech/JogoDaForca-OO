package services;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import entities.Jogador;

public class GerenciadorRanking {
    private static final String ARQUIVO_RANKING = "ranking.csv";
    private final List<Jogador> jogadores;

    public GerenciadorRanking() {
        this.jogadores = new ArrayList<>();
        carregarRanking();
    }

    public void adicionarOuAtualizarJogador(Jogador jogador) {
    for (Jogador j : jogadores) {
        if (j.getNome().equalsIgnoreCase(jogador.getNome())) {
            // Atualiza a pontuação total do jogador existente
            j.setPontuacaoTotal(jogador.getPontuacaoTotal());
            salvarRanking(); // Salva o ranking atualizado
            return;
        }
    }
    // Adiciona um novo jogador ao ranking
    jogadores.add(jogador);
    salvarRanking(); // Salva o ranking atualizado
}
    public List<Jogador> getRanking() {
        jogadores.sort(Comparator.comparingInt(Jogador::getPontuacaoTotal).reversed());
        return jogadores;
    }

    private void salvarRanking() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_RANKING))) {
        for (Jogador jogador : jogadores) {
            writer.println(jogador.getNome() + ";" + jogador.getPontuacaoTotal());
        }
    } catch (IOException e) {
        System.err.println("Erro ao salvar o ranking: " + e.getMessage());
    }
}
    private void carregarRanking() {
    File arquivo = new File(ARQUIVO_RANKING);
    if (!arquivo.exists()) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if (dados.length == 2) {
                String nome = dados[0];
                int pontuacao = Integer.parseInt(dados[1]);
                jogadores.add(new Jogador(nome, pontuacao));
            }
        }
    } catch (IOException e) {
        System.err.println("Erro ao carregar o ranking: " + e.getMessage());
    }
}
}