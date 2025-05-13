package repositories;

import entities.Jogador;
import exceptions.RepositoryException;
import java.util.List;

public interface JogadorRepository {
    void inserir(Jogador jogador) throws RepositoryException;
    void atualizar(Jogador jogador) throws RepositoryException;
    void remover(Jogador jogador) throws RepositoryException;
    Jogador buscarPorId(Long id) throws RepositoryException;
    Jogador getPorNome(String nome) throws RepositoryException;
    List<Jogador> getTodos() throws RepositoryException;
}