package repositories;

import entities.Palavra;
import entities.Tema;
import exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaPalavraRepository implements PalavraRepository {
    protected static MemoriaPalavraRepository soleInstance;
    protected List<Palavra> palavras = new ArrayList<>();
    protected Long proximoId = 1L;

    protected MemoriaPalavraRepository() {}

    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        palavra.setId(proximoId++);
        palavras.add(palavra);
    }

    @Override
    public List<Palavra> getPorTema(Tema tema) throws RepositoryException {
        List<Palavra> resultado = new ArrayList<>();
        for (Palavra p : palavras) {
            if (p.getTema().equals(tema)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public Palavra getPalavra(String texto) throws RepositoryException {
        for (Palavra p : palavras) {
            if (p.getTexto().equalsIgnoreCase(texto)) {
                return p;
            }
        }
        return null;
    }
}