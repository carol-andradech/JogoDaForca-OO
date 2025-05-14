package services;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import entities.Jogador;

// Classe responsável por gerenciar o ranking dos jogadores.
// O ranking é armazenado em um arquivo CSV e carregado na memória durante a execução.
public class GerenciadorRanking {
    private static final String ARQUIVO_RANKING = "ranking.csv"; // Nome do arquivo onde o ranking é salvo.
    private final List<Jogador> jogadores; // Lista de jogadores que compõem o ranking.

    // Construtor que inicializa a lista de jogadores e carrega os dados do arquivo.
    public GerenciadorRanking() {
        this.jogadores = new ArrayList<>(); // Inicializa a lista de jogadores.
        carregarRanking(); // Carrega os dados do arquivo para a lista.
    }

    // Adiciona um novo jogador ou atualiza a pontuação de um jogador existente no ranking.
    public void adicionarOuAtualizarJogador(Jogador jogador) {
        for (Jogador j : jogadores) { // Itera sobre a lista de jogadores.
            if (j.getNome().equalsIgnoreCase(jogador.getNome())) { // Verifica se o jogador já existe (comparação por nome, ignorando maiúsculas/minúsculas).
                // Atualiza a pontuação total do jogador existente.
                j.setPontuacaoTotal(jogador.getPontuacaoTotal());
                salvarRanking(); // Salva o ranking atualizado no arquivo.
                return; // Sai do método, pois o jogador já foi atualizado.
            }
        }
        // Caso o jogador não exista, adiciona-o ao ranking.
        jogadores.add(jogador);
        salvarRanking(); // Salva o ranking atualizado no arquivo.
    }

    // Retorna a lista de jogadores ordenada por pontuação (do maior para o menor).
    public List<Jogador> getRanking() {
        // Ordena a lista de jogadores com base na pontuação total em ordem decrescente.
        jogadores.sort(Comparator.comparingInt(Jogador::getPontuacaoTotal).reversed());
        return jogadores; // Retorna a lista ordenada.
    }

    // Salva o ranking atual no arquivo CSV.
    private void salvarRanking() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_RANKING))) {
            // Itera sobre a lista de jogadores e escreve cada jogador no arquivo.
            for (Jogador jogador : jogadores) {
                writer.println(jogador.getNome() + ";" + jogador.getPontuacaoTotal()); // Formato: nome;pontuacao
            }
        } catch (IOException e) {
            // Exibe uma mensagem de erro caso ocorra um problema ao salvar o arquivo.
            System.err.println("Erro ao salvar o ranking: " + e.getMessage());
        }
    }

    // Carrega os dados do ranking a partir do arquivo CSV.
    private void carregarRanking() {
        File arquivo = new File(ARQUIVO_RANKING); // Cria uma referência ao arquivo.
        if (!arquivo.exists()) return; // Se o arquivo não existir, não há nada para carregar.

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            // Lê o arquivo linha por linha.
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";"); // Divide a linha em partes separadas por ";".
                if (dados.length == 2) { // Verifica se a linha contém exatamente 2 campos (nome e pontuação).
                    String nome = dados[0]; // Primeiro campo: nome do jogador.
                    int pontuacao = Integer.parseInt(dados[1]); // Segundo campo: pontuação do jogador.
                    jogadores.add(new Jogador(nome, pontuacao)); // Adiciona o jogador à lista.
                }
            }
        } catch (IOException e) {
            // Exibe uma mensagem de erro caso ocorra um problema ao carregar o arquivo.
            System.err.println("Erro ao carregar o ranking: " + e.getMessage());
        }
    }
}