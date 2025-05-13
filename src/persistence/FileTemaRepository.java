package persistence;

import repositories.MemoriaTemaRepository;
import entities.Tema;
import exceptions.RepositoryException;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileTemaRepository extends MemoriaTemaRepository {
    private static final String ARQUIVO = "temas.csv";
    private static FileTemaRepository soleInstance;
    private Set<Long> idsExistentes;

    static {
        try {
            soleInstance = new FileTemaRepository();
        } catch (RepositoryException e) {
            throw new RuntimeException("Falha ao inicializar repositório", e);
        }
    }

    private FileTemaRepository() throws RepositoryException {
        super();
        this.idsExistentes = new HashSet<>();
        carregarDados();
    }

    public static FileTemaRepository getSoleInstance() {
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
                    if (dados.length < 2) continue; // Linha inválida
                    long id = Long.parseLong(dados[0]);
                    if (!idsExistentes.contains(id)) {  // Verifica se já existe
                        Tema tema = new Tema(dados[1]);
                        tema.setId(id);
                        super.inserir(tema);
                        idsExistentes.add(id); // Adiciona o ID para evitar duplicação
                    }
                }
            }
        } catch (IOException e) {
            throw new RepositoryException("Erro ao carregar temas: " + e.getMessage());
        }
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        super.inserir(tema);
        salvarDados();
    }

    private void salvarDados() throws RepositoryException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Tema t : super.temas) {
                pw.println(t.getId() + ";" + t.getNome());
            }
        } catch (IOException e) {
            throw new RepositoryException("Erro ao salvar temas: " + e.getMessage());
        }
    }
}
