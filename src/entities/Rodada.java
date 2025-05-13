package entities;

import java.util.*;

public class Rodada {
    private Jogador jogador;
    private List<Palavra> palavras;
    private Set<Character> letrasTentadas = new HashSet<>();
    private int erros = 0;
    private boolean arriscou = false;
    private int pontos = 0;
    private boolean acertouArriscar = false;

    public Rodada(Jogador jogador, List<Palavra> palavras) {
        this.jogador = jogador;
        this.palavras = palavras;
    }

    public boolean tentarLetra(char letra) {
        letra = Character.toUpperCase(letra);

        // Ignora se já tentou antes
        if (letrasTentadas.contains(letra)) return false;

        letrasTentadas.add(letra);

        boolean acertou = false;
        for (Palavra palavra : palavras) {
            if (palavra.getTexto().toUpperCase().indexOf(letra) >= 0) {
                acertou = true;
            }
        }

        if (!acertou) erros++;
        return acertou;
    }

    public boolean arriscar(List<String> palavrasArriscadas) {
        if (arriscou) return false; // Só pode uma vez
        arriscou = true;

        if (palavrasArriscadas.size() != palavras.size()) return false;

        for (int i = 0; i < palavras.size(); i++) {
            String original = palavras.get(i).getTexto().trim().toUpperCase();
            String tentativa = palavrasArriscadas.get(i).trim().toUpperCase();

            if (!original.equals(tentativa)) {
                return false;
            }
        }

        acertouArriscar = true;
        return true;
    }

    public boolean terminou() {
        return erros >= 10 || acertouArriscar || todasLetrasReveladas();
    }

    private boolean todasLetrasReveladas() {
        for (Palavra palavra : palavras) {
            for (char letra : palavra.getTexto().toUpperCase().toCharArray()) {
                if (!letrasTentadas.contains(letra)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void calcularPontos() {
        if ((acertouArriscar || todasLetrasReveladas()) && erros < 10) {
            pontos = 100;

            for (Palavra palavra : palavras) {
                for (char letra : palavra.getTexto().toUpperCase().toCharArray()) {
                    if (!letrasTentadas.contains(letra)) {
                        pontos += 15;
                    }
                }
            }
        } else {
            pontos = 0;
        }

        jogador.adicionarPontos(pontos);
    }

    // Getters
    public Jogador getJogador() { return jogador; }
    public int getErros() { return erros; }
    public Set<Character> getLetrasTentadas() { return letrasTentadas; }
    public List<Palavra> getPalavras() { return palavras; }
    public int getPontos() { return pontos; }
    public boolean jaArriscou() { return arriscou; }
    public boolean acertouArriscar() { return acertouArriscar; }
}
