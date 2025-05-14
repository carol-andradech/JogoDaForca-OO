package services;

import entities.Jogador;
import repositories.MemoriaJogadorRepository;
import repositories.JogadorRepository;
import exceptions.RepositoryException;

// Classe responsável por gerenciar as operações relacionadas aos jogadores.
// Atua como intermediária entre a lógica do jogo e o repositório de jogadores.
public class GerenciadorJogador {
    private JogadorRepository jogadorRepo; // Repositório de jogadores (interface para persistência)

    // Construtor que inicializa o repositório de jogadores.
    // Aqui, utiliza-se uma implementação em memória (Singleton).
    public GerenciadorJogador() {
        this.jogadorRepo = MemoriaJogadorRepository.getSoleInstance(); // Obtém a instância única do repositório em memória.
    }

    // Cria um novo jogador e o insere no repositório.
    // Parâmetros:
    // - nome: Nome do jogador a ser criado.
    // Retorna:
    // - O objeto Jogador criado.
    // Lança:
    // - RepositoryException: Caso ocorra um erro ao inserir o jogador no repositório.
    public Jogador criarJogador(String nome) throws RepositoryException {
        Jogador jogador = new Jogador(nome); // Cria um novo jogador com o nome fornecido.
        jogadorRepo.inserir(jogador); // Insere o jogador no repositório.
        return jogador; // Retorna o jogador criado.
    }

    // Busca um jogador no repositório pelo nome.
    // Parâmetros:
    // - nome: Nome do jogador a ser buscado.
    // Retorna:
    // - O objeto Jogador correspondente ao nome fornecido.
    // Lança:
    // - RepositoryException: Caso ocorra um erro ao buscar o jogador no repositório.
    public Jogador buscarJogador(String nome) throws RepositoryException {
        return jogadorRepo.getPorNome(nome); // Busca o jogador no repositório pelo nome.
    }
}