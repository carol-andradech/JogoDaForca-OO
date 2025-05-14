package repositories;

import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.util.List;

// conjunto de métodos que uma classe deve implementar.
//Especifica o contrato que as classes devem seguir, sem fornecer a implementação dos métodos.
public interface PalavraRepository {
    void inserir(Palavra palavra) throws RepositoryException;
    List<Palavra> getPorTema(Tema tema) throws RepositoryException;
    Palavra getPalavra(String texto) throws RepositoryException;
}