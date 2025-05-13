package repositories;

import entities.Jogador;
import exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaJogadorRepository implements JogadorRepository {
    private static MemoriaJogadorRepository soleInstance;
    private List<Jogador> jogadores = new ArrayList<>();
    private Long proximoId = 1L;

    private MemoriaJogadorRepository() {}

    public static MemoriaJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public void inserir(Jogador jogador) throws RepositoryException {
        jogador.setId(proximoId++);
        jogadores.add(jogador);
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException {
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getId().equals(jogador.getId())) {
                jogadores.set(i, jogador);
                return;
            }
        }
        throw new RepositoryException("Jogador não encontrado para atualização");
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException {
        if (!jogadores.remove(jogador)) {
            throw new RepositoryException("Jogador não encontrado para remoção");
        }
    }

    @Override
    public Jogador buscarPorId(Long id) throws RepositoryException {
        for (Jogador j : jogadores) {
            if (j.getId().equals(id)) {
                return j;
            }
        }
        throw new RepositoryException("Jogador não encontrado");
    }

    @Override
    public Jogador getPorNome(String nome) throws RepositoryException {
        for (Jogador j : jogadores) {
            if (j.getNome().equalsIgnoreCase(nome)) {
                return j;
            }
        }
        return null;
    }

    @Override
    public List<Jogador> getTodos() throws RepositoryException {
        return new ArrayList<>(jogadores);
    }
}