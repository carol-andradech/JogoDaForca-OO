package repositories;

import entities.Tema;
import exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por gerenciar o repositório de temas em memória.
// Implementa a interface TemaRepository, fornecendo a lógica para manipulação de temas.
public class MemoriaTemaRepository implements TemaRepository {
    private static MemoriaTemaRepository soleInstance; // Instância única (Singleton) do repositório.
    protected List<Tema> temas = new ArrayList<>(); // Lista que armazena os temas em memória.
    protected Long proximoId = 1L; // Gerador de IDs únicos para os temas.

    // Construtor protegido para permitir herança.
    // Impede que outras classes criem instâncias diretamente.
    protected MemoriaTemaRepository() {}

    // Método para obter a instância única do repositório (Singleton).
    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) { // Verifica se a instância já foi criada.
            soleInstance = new MemoriaTemaRepository(); // Cria a instância única se ainda não existir.
        }
        return soleInstance; // Retorna a instância única.
    }

    // Insere um novo tema no repositório.
    // Parâmetros:
    // - tema: Objeto Tema a ser inserido.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao inserir o tema.
    @Override
    public void inserir(Tema tema) throws RepositoryException {
        tema.setId(proximoId++); // Define um ID único para o tema e incrementa o contador.
        temas.add(tema); // Adiciona o tema à lista de temas.
    }

    // Retorna todos os temas armazenados no repositório.
    // Retorna:
    // - Uma nova lista contendo todos os objetos Tema armazenados.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao obter os temas.
    @Override
    public List<Tema> getTodos() throws RepositoryException {
        return new ArrayList<>(temas); // Retorna uma cópia da lista de temas para evitar modificações externas.
    }

    // Busca um tema no repositório pelo nome.
    // Parâmetros:
    // - nome: Nome do tema a ser buscado.
    // Retorna:
    // - O objeto Tema correspondente ao nome fornecido, ou null se não for encontrado.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao buscar o tema.
    @Override
    public Tema getPorNome(String nome) throws RepositoryException {
        for (Tema t : temas) { // Itera sobre a lista de temas.
            if (t.getNome().equalsIgnoreCase(nome)) return t; // Compara os nomes ignorando maiúsculas/minúsculas.
        }
        return null; // Retorna null se o tema não for encontrado.
    }
}