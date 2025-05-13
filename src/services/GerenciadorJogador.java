package services;

import entities.Jogador;
import repositories.MemoriaJogadorRepository;
import repositories.JogadorRepository;
import exceptions.RepositoryException;

public class GerenciadorJogador {
    private JogadorRepository jogadorRepo;

    public GerenciadorJogador() {
        this.jogadorRepo = MemoriaJogadorRepository.getSoleInstance();
    }

    public Jogador criarJogador(String nome) throws RepositoryException {
        Jogador jogador = new Jogador(nome);
        jogadorRepo.inserir(jogador);
        return jogador;
    }

    public Jogador buscarJogador(String nome) throws RepositoryException {
        return jogadorRepo.getPorNome(nome);
    }
}