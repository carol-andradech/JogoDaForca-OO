package repositories;

import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.util.List;

public interface PalavraRepository {
    void inserir(Palavra palavra) throws RepositoryException;
    List<Palavra> getPorTema(Tema tema) throws RepositoryException;
    Palavra getPalavra(String texto) throws RepositoryException;
}