package services;

import entities.*;
import repositories.*;
import exceptions.RepositoryException;

import java.util.List;
import java.util.Random;
import java.util.Collections; // <-- IMPORT NECESSÁRIO

public class GerenciadorRodada {
    private final TemaRepository temaRepo;
    private final PalavraRepository palavraRepo;
    private final Random random = new Random();

    public GerenciadorRodada(TemaRepository temaRepo, PalavraRepository palavraRepo) {
        this.temaRepo = temaRepo;
        this.palavraRepo = palavraRepo;
    }

    public Rodada novaRodada(Jogador jogador) throws RepositoryException {
        // Obtém todos os temas disponíveis
        List<Tema> temas = temaRepo.getTodos();
        if (temas.isEmpty()) {
            throw new RepositoryException("Nenhum tema cadastrado.");
        }

        // Escolhe um tema aleatório
        Tema tema = temas.get(random.nextInt(temas.size()));

        // Obtém as palavras do tema escolhido
        List<Palavra> palavrasTema = palavraRepo.getPorTema(tema);
        if (palavrasTema.size() < 3) {
            throw new RepositoryException("Tema '" + tema.getNome() + "' não possui pelo menos 3 palavras.");
        }

        // Embaralha e seleciona 3 palavras diferentes
        Collections.shuffle(palavrasTema);
        List<Palavra> palavrasEscolhidas = palavrasTema.subList(0, 3);

        // Cria e retorna a rodada com essas 3 palavras
        return new Rodada(jogador, palavrasEscolhidas);
    }
}
