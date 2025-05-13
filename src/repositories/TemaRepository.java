// src/repositories/TemaRepository.java
package repositories;

import entities.Tema;
import exceptions.RepositoryException;
import java.util.List;

public interface TemaRepository {
    void inserir(Tema tema) throws RepositoryException;
    List<Tema> getTodos() throws RepositoryException;
    Tema getPorNome(String nome) throws RepositoryException;
}