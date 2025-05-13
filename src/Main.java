import repositories.PalavraRepository;
import repositories.TemaRepository;
import services.*;
import entities.*;
import persistence.*;
import utils.*;
import exceptions.RepositoryException;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static GerenciadorJogador gerenciadorJogador = new GerenciadorJogador();
    private static GerenciadorRodada gerenciadorRodada;
    private static TemaRepository temaRepo;
    private static PalavraRepository palavraRepo;

    public static void main(String[] args) {
        try {
            inicializarSistema();
            exibirMenuPrincipal();
        } catch (RepositoryException e) {
            System.out.println("Erro fatal: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void inicializarSistema() throws RepositoryException {
        temaRepo = FileTemaRepository.getSoleInstance();
        palavraRepo = FilePalavraRepository.getSoleInstance();
        gerenciadorRodada = new GerenciadorRodada(temaRepo, palavraRepo);
    }

    private static void exibirMenuPrincipal() throws RepositoryException {
        while(true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Jogar");
            System.out.println("2. Cadastrar Tema");
            System.out.println("3. Cadastrar Palavra");
            System.out.println("4. Ranking");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();

            switch(opcao) {
                case "1": iniciarJogo(); break;
                case "2": cadastrarTema(); break;
                case "3": cadastrarPalavra(); break;
                case "4": exibirRanking(); break;
                case "5": return;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private static void iniciarJogo() throws RepositoryException {
        System.out.print("\nDigite seu nome: ");
        String nome = scanner.nextLine();
        Jogador jogador = gerenciadorJogador.criarJogador(nome);

        boolean jogarNovamente;
        do {
            Rodada rodada = gerenciadorRodada.novaRodada(jogador);
            executarRodada(rodada);

            System.out.print("\nJogar novamente? (S/N): ");
            jogarNovamente = scanner.nextLine().equalsIgnoreCase("S");
        } while(jogarNovamente);

        System.out.println("\n=== Pontuação Total: " + jogador.getPontuacaoTotal() + " ===");
    }

    private static void executarRodada(Rodada rodada) {
        if (!rodada.getPalavras().isEmpty()) {
            Tema temaDaRodada = rodada.getPalavras().get(0).getTema();
            System.out.println("\n=== TEMA DA RODADA: " + temaDaRodada.getNome().toUpperCase() + " ===");
        }
        while(!rodada.terminou()) {
            exibirStatus(rodada);

            System.out.print("\nDigite uma letra ou 'ARRI$CAR': ");
            String input = scanner.nextLine().trim().toUpperCase();

            if(input.equals("ARRI$CAR")) {
                processarArriscada(rodada);
                break;
            } else if(input.length() == 1 && Character.isLetter(input.charAt(0))) {
                rodada.tentarLetra(input.charAt(0));
            } else {
                System.out.println("Entrada inválida! Digite uma letra ou 'ARRI$CAR'");
            }
        }

        rodada.calcularPontos();
        exibirResultado(rodada);
    }

    private static void exibirStatus(Rodada rodada) {
        System.out.println("\n=== Status da Rodada ===");
        System.out.println("Tema: " + rodada.getPalavras().get(0).getTema().getNome());
        System.out.println("Palavras: " + mascararPalavras(rodada));
        System.out.println("Letras usadas: " + rodada.getLetrasTentadas());
        System.out.println("Erros: " + rodada.getErros() + "/10");
        System.out.println(Boneco.montar(rodada.getErros()));
    }

    private static String mascararPalavras(Rodada rodada) {
        StringBuilder sb = new StringBuilder();
        for(Palavra palavra : rodada.getPalavras()) {
            for(char c : palavra.getTexto().toCharArray()) {
                sb.append(rodada.getLetrasTentadas().contains(c) ? c : '_').append(' ');
            }
            sb.append("  ");
        }
        return sb.toString();
    }

    private static void processarArriscada(Rodada rodada) {
        System.out.print("Digite as palavras exatas separadas por espaço: ");
        List<String> tentativa = Arrays.asList(scanner.nextLine().trim().split(" "));
        boolean acerto = rodada.arriscar(tentativa);

        if(acerto) {
            System.out.println("\n>>> Parabéns! Você acertou todas as palavras! <<<");
        } else {
            System.out.println("\nXXX Errou! As palavras corretas eram: XXX");
            for(Palavra p : rodada.getPalavras()) {
                System.out.print(p.getTexto() + " ");
            }
            System.out.println();
        }
    }

    private static void exibirResultado(Rodada rodada) {
        System.out.println("\n=== Resultado da Rodada ===");
        System.out.println("Erros cometidos: " + rodada.getErros());
        System.out.println("Pontos conquistados: " + rodada.getPontos());
        System.out.println("Pontuação total: " + rodada.getJogador().getPontuacaoTotal());
    }

    private static void cadastrarTema() throws RepositoryException {
        System.out.print("Nome do tema: ");
        String nome = scanner.nextLine().trim();

        if(Validador.ehTemaValido(nome)) {
            temaRepo.inserir(new Tema(nome));
            System.out.println("Tema cadastrado!");
        } else {
            System.out.println("Nome inválido! Use apenas letras e espaços (3-20 caracteres)");
        }
    }

    private static void cadastrarPalavra() throws RepositoryException {
        System.out.print("Tema da palavra: ");
        String nomeTema = scanner.nextLine().trim();
        Tema tema = temaRepo.getPorNome(nomeTema);

        if(tema == null) {
            System.out.println("Tema não encontrado!");
            return;
        }

        System.out.print("Nova palavra: ");
        String texto = scanner.nextLine().trim().toUpperCase();

        if(Validador.ehPalavraValida(texto)) {
            palavraRepo.inserir(new Palavra(texto, tema));
            System.out.println("Palavra cadastrada!");
        } else {
            System.out.println("Palavra inválida! Use apenas letras (3-15 caracteres)");
        }
    }

    private static void exibirRanking() {
        // Implementação do ranking pode ser adicionada aqui
        System.out.println("\n=== Ranking ===");
        System.out.println("Funcionalidade em desenvolvimento!");
    }
}