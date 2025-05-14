import services.*;
import entities.*;
import persistence.*;
import utils.*;
import java.util.*;
import repositories.PalavraRepository;
import repositories.TemaRepository;
import exceptions.RepositoryException;

public class Main {
    // Scanner para entrada de dados do usuário
    private static final Scanner scanner = new Scanner(System.in);

    // Gerenciadores e repositórios usados no sistema
    private static GerenciadorJogador gerenciadorJogador = new GerenciadorJogador(); // Gerencia os jogadores
    private static GerenciadorRodada gerenciadorRodada; // Gerencia as rodadas do jogo
    private static TemaRepository temaRepo; // Repositório para temas
    private static PalavraRepository palavraRepo; // Repositório para palavras
    private static GerenciadorRanking gerenciadorRanking; // Gerencia o ranking dos jogadores

    public static void main(String[] args) {
        try {
            inicializarSistema(); // Inicializa os componentes do sistema
            exibirMenuPrincipal(); // Exibe o menu principal e gerencia as opções do usuário
        } catch (RepositoryException e) {
            // Trata erros relacionados aos repositórios
            System.out.println("Erro fatal: " + e.getMessage());
        } finally {
            // Fecha o scanner ao final da execução
            scanner.close();
        }
    }

    // Inicializa os componentes do sistema, como repositórios e gerenciadores
    private static void inicializarSistema() throws RepositoryException {
        temaRepo = FileTemaRepository.getSoleInstance(); // Inicializa o repositório de temas
        palavraRepo = FilePalavraRepository.getSoleInstance(); // Inicializa o repositório de palavras
        gerenciadorRodada = new GerenciadorRodada(temaRepo, palavraRepo); // Cria o gerenciador de rodadas com os repositórios
        gerenciadorRanking = new GerenciadorRanking(); // Inicializa o gerenciador de ranking
    }

    // Exibe o menu principal e gerencia as opções escolhidas pelo usuário
    private static void exibirMenuPrincipal() throws RepositoryException {
        while (true) {
            // Exibe as opções do menu
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Jogar");
            System.out.println("2. Cadastrar Tema");
            System.out.println("3. Cadastrar Palavra");
            System.out.println("4. Ranking");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine(); // Lê a opção do usuário

            // Executa a ação correspondente à opção escolhida
            switch (opcao) {
                case "1": iniciarJogo(); break; // Inicia o jogo
                case "2": cadastrarTema(); break; // Cadastra um novo tema
                case "3": cadastrarPalavra(); break; // Cadastra uma nova palavra
                case "4": exibirRanking(); break; // Exibe o ranking dos jogadores
                case "5": return; // Sai do programa
                default: System.out.println("Opção inválida!"); // Trata opções inválidas
            }
        }
    }

    // Inicia o jogo para um jogador
    private static void iniciarJogo() throws RepositoryException {
    System.out.print("\nDigite seu nome: ");
    String nome = scanner.nextLine(); // Lê o nome do jogador
    Jogador jogador = gerenciadorJogador.criarJogador(nome); // Cria ou recupera o jogador

    boolean jogarNovamente;
    do {
        // Cria uma nova rodada para o jogador
        Rodada rodada = gerenciadorRodada.novaRodada(jogador);
        executarRodada(rodada); // Executa a lógica da rodada

        // Atualiza o ranking com a pontuação total do jogador
        gerenciadorRanking.adicionarOuAtualizarJogador(jogador);

        // Pergunta se o jogador quer jogar novamente
        System.out.print("\nJogar novamente? (S/N): ");
        jogarNovamente = scanner.nextLine().equalsIgnoreCase("S");
    } while (jogarNovamente);

    // Exibe a pontuação total do jogador ao final
    System.out.println("\n=== Pontuação Total: " + jogador.getPontuacaoTotal() + " ===");
}

    // Executa a lógica de uma rodada do jogo
    private static void executarRodada(Rodada rodada) {
        // Exibe o tema da rodada
        if (!rodada.getPalavras().isEmpty()) {
            Tema temaDaRodada = rodada.getPalavras().get(0).getTema();
            System.out.println("\n=== TEMA DA RODADA: " + temaDaRodada.getNome().toUpperCase() + " ===");
        }

        // Loop principal da rodada
        while (!rodada.terminou()) {
            exibirStatus(rodada); // Exibe o status atual da rodada

            // Lê a entrada do jogador
            System.out.print("\nDigite uma letra ou 'ARRI$CAR': ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("ARRI$CAR")) {
                processarArriscada(rodada); // Processa a tentativa de adivinhar todas as palavras
                break;
            } else if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                rodada.tentarLetra(input.charAt(0)); // Tenta uma letra
            } else {
                System.out.println("Entrada inválida! Digite uma letra ou 'ARRI$CAR'");
            }
        }

        rodada.calcularPontos(); // Calcula os pontos da rodada
        exibirResultado(rodada); // Exibe o resultado da rodada
    }

    // Exibe o status atual da rodada
    private static void exibirStatus(Rodada rodada) {
        System.out.println("\n=== Status da Rodada ===");
        System.out.println("Tema: " + rodada.getPalavras().get(0).getTema().getNome());
        System.out.println("Palavras: " + mascararPalavras(rodada));
        System.out.println("Letras usadas: " + rodada.getLetrasTentadas());
        System.out.println("Erros: " + rodada.getErros() + "/10");
        System.out.println(Boneco.montar(rodada.getErros())); // Exibe o boneco do jogo da forca
    }

    // Mascarar as palavras da rodada (exibe letras acertadas e oculta as restantes)
    private static String mascararPalavras(Rodada rodada) {
        StringBuilder sb = new StringBuilder();
        for (Palavra palavra : rodada.getPalavras()) {
            for (char c : palavra.getTexto().toCharArray()) {
                sb.append(rodada.getLetrasTentadas().contains(c) ? c : '_').append(' ');
            }
            sb.append("  ");
        }
        return sb.toString();
    }

    // Processa a tentativa de adivinhar todas as palavras
    private static void processarArriscada(Rodada rodada) {
        System.out.print("Digite as palavras exatas separadas por espaço: ");
        List<String> tentativa = Arrays.asList(scanner.nextLine().trim().split(" "));
        boolean acerto = rodada.arriscar(tentativa); // Verifica se o jogador acertou todas as palavras

        if (acerto) {
            System.out.println("\n>>> Parabéns! Você acertou todas as palavras! <<<");
        } else {
            System.out.println("\nXXX Errou! As palavras corretas eram: XXX");
            for (Palavra p : rodada.getPalavras()) {
                System.out.print(p.getTexto() + " ");
            }
            System.out.println();
        }
    }

    // Exibe o resultado da rodada
    private static void exibirResultado(Rodada rodada) {
        System.out.println("\n=== Resultado da Rodada ===");
        System.out.println("Erros cometidos: " + rodada.getErros());
        System.out.println("Pontos conquistados: " + rodada.getPontos());
        System.out.println("Pontuação total: " + rodada.getJogador().getPontuacaoTotal());
    }

    // Cadastra um novo tema no sistema
    private static void cadastrarTema() throws RepositoryException {
        System.out.print("Nome do tema: ");
        String nome = scanner.nextLine().trim();

        if (Validador.ehTemaValido(nome)) {
            temaRepo.inserir(new Tema(nome)); // Insere o tema no repositório
            System.out.println("Tema cadastrado!");
        } else {
            System.out.println("Nome inválido! Use apenas letras e espaços (3-20 caracteres)");
        }
    }

    // Cadastra uma nova palavra associada a um tema
    private static void cadastrarPalavra() throws RepositoryException {
        System.out.print("Tema da palavra: ");
        String nomeTema = scanner.nextLine().trim();
        Tema tema = temaRepo.getPorNome(nomeTema); // Busca o tema pelo nome

        if (tema == null) {
            System.out.println("Tema não encontrado!");
            return;
        }

        System.out.print("Nova palavra: ");
        String texto = scanner.nextLine().trim().toUpperCase();

        if (Validador.ehPalavraValida(texto)) {
            palavraRepo.inserir(new Palavra(texto, tema)); // Insere a palavra no repositório
            System.out.println("Palavra cadastrada!");
        } else {
            System.out.println("Palavra inválida! Use apenas letras (3-15 caracteres)");
        }
    }

    // Exibe o ranking dos jogadores
    private static void exibirRanking() {
        System.out.println("\n=== Ranking ===");
        List<Jogador> ranking = gerenciadorRanking.getRanking(); // Obtém o ranking dos jogadores

        if (ranking.isEmpty()) {
            System.out.println("Nenhum jogador registrado no ranking ainda.");
            return;
        }

        int posicao = 1;
        for (Jogador jogador : ranking) {
            System.out.printf("%d. %s - %d pontos%n", posicao++, jogador.getNome(), jogador.getPontuacaoTotal());
        }
    }
}