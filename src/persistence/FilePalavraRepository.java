package persistence;

import repositories.MemoriaPalavraRepository;
import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FilePalavraRepository extends MemoriaPalavraRepository {
    private static final String ARQUIVO = "palavras.csv";
    private static FilePalavraRepository soleInstance;
    private Set<Long> idsExistentes;

    static {
        try {
            soleInstance = new FilePalavraRepository();
        } catch (RepositoryException e) {
            throw new RuntimeException("Falha ao inicializar repositório", e);
        }
    }

    private FilePalavraRepository() throws RepositoryException {
        super();
        this.idsExistentes = new HashSet<>();
        carregarDados();
    }

    public static FilePalavraRepository getSoleInstance() {
        return soleInstance;
    }

    private void carregarDados() throws RepositoryException {
        File arquivo = new File(ARQUIVO);

        try {
            // Cria o arquivo se não existir
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                return; // Arquivo novo, não há dados para carregar
            }

            try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (dados.length < 4) continue; // Linha inválida
                    long id = Long.parseLong(dados[0]);
                    if (!idsExistentes.contains(id)) { // Verifica se já existe
                        Tema tema = new Tema(dados[2]);
                        tema.setId(Long.parseLong(dados[1]));
                        Palavra palavra = new Palavra(dados[3], tema);
                        palavra.setId(id);
                        super.inserir(palavra);
                        idsExistentes.add(id); // Adiciona o ID para evitar duplicação
                    }
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Erro ao carregar palavras: " + e.getMessage());
        }
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        super.inserir(palavra);
        salvarDados();
    }

    private void salvarDados() throws RepositoryException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Palavra p : super.palavras) {
                pw.println(p.getId() + ";" + p.getTema().getId() + ";" + p.getTema().getNome() + ";" + p.getTexto());
            }
        } catch (IOException e) {
            throw new RepositoryException("Erro ao salvar palavras: " + e.getMessage());
        }
    }
}
