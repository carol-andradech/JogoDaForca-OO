
package repositories;

import entities.Tema;
import exceptions.RepositoryException;
import java.util.List;

// conjunto de métodos que uma classe deve implementar.
//Especifica o contrato que as classes devem seguir, sem fornecer a implementação dos métodos.
public interface TemaRepository {
    void inserir(Tema tema) throws RepositoryException;
    List<Tema> getTodos() throws RepositoryException;
    Tema getPorNome(String nome) throws RepositoryException;
}