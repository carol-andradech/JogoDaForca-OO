package repositories;

import entities.Tema;
import exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaTemaRepository implements TemaRepository {
    private static MemoriaTemaRepository soleInstance;
    protected List<Tema> temas = new ArrayList<>();
    protected Long proximoId = 1L;

    // Construtor protegido para permitir herança
    protected MemoriaTemaRepository() {}

    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        tema.setId(proximoId++);
        temas.add(tema);
    }

    @Override
    public List<Tema> getTodos() throws RepositoryException {
        return new ArrayList<>(temas);
    }

    @Override
    public Tema getPorNome(String nome) throws RepositoryException {
        for (Tema t : temas) {
            if (t.getNome().equalsIgnoreCase(nome)) return t;
        }
        return null;
    }
}