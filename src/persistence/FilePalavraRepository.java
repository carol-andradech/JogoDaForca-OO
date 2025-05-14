package persistence;

import repositories.MemoriaPalavraRepository;
import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Classe responsável por gerenciar o repositório de palavras utilizando persistência em arquivo.
// Estende a classe MemoriaPalavraRepository para reutilizar a lógica de armazenamento em memória.
public class FilePalavraRepository extends MemoriaPalavraRepository {
    private static final String ARQUIVO = "palavras.csv"; // Nome do arquivo onde as palavras são armazenadas.
    private static FilePalavraRepository soleInstance; // Instância única (Singleton) do repositório.
    private Set<Long> idsExistentes; // Conjunto para rastrear IDs já utilizados e evitar duplicação.

    // Bloco estático para inicializar a instância única do repositório.
    static {
        try {
            soleInstance = new FilePalavraRepository(); // Inicializa a instância única.
        } catch (RepositoryException e) {
            // Lança uma exceção em tempo de execução caso ocorra um erro ao inicializar o repositório.
            throw new RuntimeException("Falha ao inicializar repositório", e);
        }
    }

    // Construtor privado para implementar o padrão Singleton.
    // Inicializa o conjunto de IDs existentes e carrega os dados do arquivo.
    private FilePalavraRepository() throws RepositoryException {
        super(); // Chama o construtor da classe base (MemoriaPalavraRepository).
        this.idsExistentes = new HashSet<>(); // Inicializa o conjunto de IDs existentes.
        carregarDados(); // Carrega os dados do arquivo para a memória.
    }

    // Método para obter a instância única do repositório.
    public static FilePalavraRepository getSoleInstance() {
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
                    if (dados.length < 4) continue; // Ignora linhas inválidas (menos de 4 campos).

                    long id = Long.parseLong(dados[0]); // Primeiro campo: ID da palavra.
                    if (!idsExistentes.contains(id)) { // Verifica se o ID já foi carregado.
                        Tema tema = new Tema(dados[2]); // Cria o tema com o nome (terceiro campo).
                        tema.setId(Long.parseLong(dados[1])); // Define o ID do tema (segundo campo).
                        Palavra palavra = new Palavra(dados[3], tema); // Cria a palavra com o texto (quarto campo) e o tema.
                        palavra.setId(id); // Define o ID da palavra.
                        super.inserir(palavra); // Insere a palavra no repositório em memória.
                        idsExistentes.add(id); // Adiciona o ID ao conjunto de IDs existentes.
                    }
                }
            }
        } catch (IOException e) {
            // Lança uma exceção personalizada caso ocorra um erro ao carregar os dados.
            throw new RepositoryException("Erro ao carregar palavras: " + e.getMessage());
        }
    }

    // Insere uma nova palavra no repositório e salva os dados no arquivo.
    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        super.inserir(palavra); // Insere a palavra no repositório em memória.
        salvarDados(); // Salva os dados atualizados no arquivo.
    }

    // Salva os dados do repositório em memória no arquivo CSV.
    private void salvarDados() throws RepositoryException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            // Itera sobre todas as palavras no repositório.
            for (Palavra p : super.palavras) {
                // Escreve cada palavra no arquivo no formato: ID;ID_TEMA;NOME_TEMA;TEXTO
                pw.println(p.getId() + ";" + p.getTema().getId() + ";" + p.getTema().getNome() + ";" + p.getTexto());
            }
        } catch (IOException e) {
            // Lança uma exceção personalizada caso ocorra um erro ao salvar os dados.
            throw new RepositoryException("Erro ao salvar palavras: " + e.getMessage());
        }
    }
}