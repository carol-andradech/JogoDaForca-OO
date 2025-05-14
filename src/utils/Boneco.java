package utils;

// A classe Boneco é responsável por montar e exibir o boneco do jogo da forca
// com base no número de erros cometidos pelo jogador

public class Boneco {
    // Array de strings que representa as partes do boneco e da estrutura da forca.
    public static String[] PARTES = {
            "  _______     \n",      // 0 - Base superior
            " |/      |    \n",      // 1 - Gancho
            " |            \n",      // 2 - Base cabeça
            " |            \n",      // 3 - Linha vertical
            " |            \n",      // 4 - Linha vertical
            " |            \n",      // 5 - Base inferior
            "_|___         \n",      // 6 - Plataforma
            " |      0     \n",      // 7 - Cabeça
            " |      |     \n",      // 8 - Tronco
            " |     /|     \n",      // 9 - Braço esquerdo
            " |     /|\\   \n",     // 10 - Braço direito
            " |      |     \n",      // 11 - Cintura
            " |     /      \n",      // 12 - Perna esquerda
            " |     / \\   \n"     // 13 - Perna direita
    };

    // Método estático que monta o boneco com base no número de erros cometidos.
    // Parâmetro:
    // - erros: número de erros cometidos pelo jogador (0 a 7).
    // Retorna:
    // - Uma string que representa o estado atual do boneco e da forca.
    public static String montar(int erros) {
        // StringBuilder é usado para construir a string do boneco de forma eficiente.
        StringBuilder sb = new StringBuilder();

         // Adiciona as partes fixas da forca (base superior e gancho).
        sb.append(PARTES[0]).append(PARTES[1]).append(PARTES[2]);

        // Adiciona as partes do boneco progressivamente com base no número de erros.
        if(erros > 0) sb.append(PARTES[7]);  // Cabeça
        if(erros > 1) sb.append(PARTES[8]);  // Tronco
        if(erros > 2) sb.append(PARTES[9]);  // Braço esquerdo
        if(erros > 3) sb.append(PARTES[10]); // Braço direito
        if(erros > 4) sb.append(PARTES[11]); // Cintura
        if(erros > 5) sb.append(PARTES[12]); // Perna esquerda
        if(erros > 6) sb.append(PARTES[13]); // Perna direita

        // Adiciona as partes fixas inferiores da forca (tronco e base da plataforma).
        sb.append(PARTES[3]).append(PARTES[4]).append(PARTES[5]).append(PARTES[6]);

        // Retorna a string completa representando o estado atual do boneco e da forca.
        return sb.toString();
    }
}