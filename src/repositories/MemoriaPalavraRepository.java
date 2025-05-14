package repositories;

import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por gerenciar o repositório de palavras em memória.
// Implementa a interface PalavraRepository, fornecendo a lógica para manipulação de palavras.
public class MemoriaPalavraRepository implements PalavraRepository {
    protected static MemoriaPalavraRepository soleInstance; // Instância única (Singleton) do repositório.
    protected List<Palavra> palavras = new ArrayList<>(); // Lista que armazena as palavras em memória.
    protected Long proximoId = 1L; // Gerador de IDs únicos para as palavras.

    // Construtor protegido para permitir herança.
    // Impede que outras classes criem instâncias diretamente.
    protected MemoriaPalavraRepository() {}

    // Método para obter a instância única do repositório (Singleton).
    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) { // Verifica se a instância já foi criada.
            soleInstance = new MemoriaPalavraRepository(); // Cria a instância única se ainda não existir.
        }
        return soleInstance; // Retorna a instância única.
    }

    // Insere uma nova palavra no repositório.
    // Parâmetros:
    // - palavra: Objeto Palavra a ser inserido.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao inserir a palavra.
    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        palavra.setId(proximoId++); // Define um ID único para a palavra e incrementa o contador.
        palavras.add(palavra); // Adiciona a palavra à lista de palavras.
    }

    // Retorna uma lista de palavras associadas a um tema específico.
    // Parâmetros:
    // - tema: Objeto Tema cujas palavras associadas serão retornadas.
    // Retorna:
    // - Uma lista de objetos Palavra associados ao tema fornecido.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao buscar as palavras.
    @Override
    public List<Palavra> getPorTema(Tema tema) throws RepositoryException {
        List<Palavra> resultado = new ArrayList<>(); // Lista para armazenar as palavras encontradas.
        for (Palavra p : palavras) { // Itera sobre todas as palavras armazenadas.
            if (p.getTema().equals(tema)) { // Verifica se o tema da palavra corresponde ao tema fornecido.
                resultado.add(p); // Adiciona a palavra à lista de resultados.
            }
        }
        return resultado; // Retorna a lista de palavras associadas ao tema.
    }

    // Busca uma palavra no repositório pelo texto.
    // Parâmetros:
    // - texto: String contendo o texto da palavra a ser buscada.
    // Retorna:
    // - O objeto Palavra correspondente ao texto fornecido, ou null se não for encontrado.
    // Lança:
    // - RepositoryException: Caso ocorra algum erro ao buscar a palavra.
    @Override
    public Palavra getPalavra(String texto) throws RepositoryException {
        for (Palavra p : palavras) { // Itera sobre todas as palavras armazenadas.
            if (p.getTexto().equalsIgnoreCase(texto)) { // Compara os textos ignorando maiúsculas/minúsculas.
                return p; // Retorna a palavra encontrada.
            }
        }
        return null; // Retorna null se a palavra não for encontrada.
    }
}