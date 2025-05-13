package utils;

public class Boneco {
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

    public static String montar(int erros) {
        StringBuilder sb = new StringBuilder();
        sb.append(PARTES[0]).append(PARTES[1]).append(PARTES[2]);

        // Progressão do boneco
        if(erros > 0) sb.append(PARTES[7]);  // Cabeça
        if(erros > 1) sb.append(PARTES[8]);  // Tronco
        if(erros > 2) sb.append(PARTES[9]);  // Braço esquerdo
        if(erros > 3) sb.append(PARTES[10]); // Braço direito
        if(erros > 4) sb.append(PARTES[11]); // Cintura
        if(erros > 5) sb.append(PARTES[12]); // Perna esquerda
        if(erros > 6) sb.append(PARTES[13]); // Perna direita

        // Parte fixa inferior
        sb.append(PARTES[3]).append(PARTES[4]).append(PARTES[5]).append(PARTES[6]);

        return sb.toString();
    }
}