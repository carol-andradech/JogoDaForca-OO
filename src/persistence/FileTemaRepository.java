package persistence;

import repositories.MemoriaTemaRepository;
import entities.Tema;
import exceptions.RepositoryException;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Classe responsável por gerenciar o repositório de temas utilizando persistência em arquivo.
// Estende a classe MemoriaTemaRepository para reutilizar a lógica de armazenamento em memória.
public class FileTemaRepository extends MemoriaTemaRepository {
    private static final String ARQUIVO = "temas.csv"; // Nome do arquivo onde os temas são armazenados.
    private static FileTemaRepository soleInstance; // Instância única (Singleton) do repositório.
    private Set<Long> idsExistentes; // Conjunto para rastrear IDs já utilizados e evitar duplicação.

    // Bloco estático para inicializar a instância única do repositório.
    static {
        try {
            soleInstance = new FileTemaRepository(); // Inicializa a instância única.
        } catch (RepositoryException e) {
            // Lança uma exceção em tempo de execução caso ocorra um erro ao inicializar o repositório.
            throw new RuntimeException("Falha ao inicializar repositório", e);
        }
    }

    // Construtor privado para implementar o padrão Singleton.
    // Inicializa o conjunto de IDs existentes e carrega os dados do arquivo.
    private FileTemaRepository() throws RepositoryException {
        super(); // Chama o construtor da classe base (MemoriaTemaRepository).
        this.idsExistentes = new HashSet<>(); // Inicializa o conjunto de IDs existentes.
        carregarDados(); // Carrega os dados do arquivo para a memória.
    }

    // Método para obter a instância única do repositório.
    public static FileTemaRepository getSoleInstance() {
        return soleInstance; // Retorna a instância única.
    }

    // Carrega os dados do arquivo CSV para o repositório em memória.
    private void carregarDados() throws RepositoryException {
        File arquivo = new File(ARQUIVO); // Cria uma referência ao arquivo.

        try {
            // Cria o arquivo se ele não existir.
            if (!arquivo.exists()) {
                arquivo.createNewFile(); // Cria um novo arquivo vazio.
                return; // Arquivo novo, não há dados para carregar.
            }

            // Lê o arquivo linha por linha.
            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";"); // Divide a linha em partes separadas por ";".
                    if (dados.length < 2) continue; // Ignora linhas inválidas (menos de 2 campos).

                    long id = Long.parseLong(dados[0]); // Primeiro campo: ID do tema.
                    if (!idsExistentes.contains(id)) { // Verifica se o ID já foi carregado.
                        Tema tema = new Tema(dados[1]); // Cria o tema com o nome (segundo campo).
                        tema.setId(id); // Define o ID do tema.
                        super.inserir(tema); // Insere o tema no repositório em memória.
                        idsExistentes.add(id); // Adiciona o ID ao conjunto de IDs existentes.
                    }
                }
            }
        } catch (IOException e) {
            // Lança uma exceção personalizada caso ocorra um erro ao carregar os dados.
            throw new RepositoryException("Erro ao carregar temas: " + e.getMessage());
        }
    }

    // Insere um novo tema no repositório e salva os dados no arquivo.
    @Override
    public void inserir(Tema tema) throws RepositoryException {
        super.inserir(tema); // Insere o tema no repositório em memória.
        salvarDados(); // Salva os dados atualizados no arquivo.
    }

    // Salva os dados do repositório em memória no arquivo CSV.
    private void salvarDados() throws RepositoryException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            // Itera sobre todos os temas no repositório.
            for (Tema t : super.temas) {
                // Escreve cada tema no arquivo no formato: ID;NOME
                pw.println(t.getId() + ";" + t.getNome());
            }
        } catch (IOException e) {
            // Lança uma exceção personalizada caso ocorra um erro ao salvar os dados.
            throw new RepositoryException("Erro ao salvar temas: " + e.getMessage());
        }
    }
}