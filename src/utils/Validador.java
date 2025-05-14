package utils;

// Classe utilitária para validação de entradas no jogo.
// Contém métodos estáticos para validar letras, temas e palavras.
public class Validador {

    // Verifica se a entrada é uma letra válida.
    // Parâmetros:
    // - input: String contendo a entrada do usuário.
    // Retorna:
    // - true se a entrada for uma única letra válida (A-Z ou a-z).
    // - false caso contrário.
    public static boolean ehLetraValida(String input) {
        return input != null && input.length() == 1 && // Verifica se a entrada não é nula e tem exatamente 1 caractere.
                Character.isLetter(input.charAt(0)); // Verifica se o caractere é uma letra.
    }

    // Verifica se o nome do tema é válido.
    // Parâmetros:
    // - nome: String contendo o nome do tema.
    // Retorna:
    // - true se o nome for composto apenas por letras e espaços, com tamanho entre 3 e 20 caracteres.
    // - false caso contrário.
    public static boolean ehTemaValido(String nome) {
        return nome != null && nome.matches("^[\\p{L} ]{3,20}$"); // Regex para validar letras (incluindo acentos) e espaços.
    }

    // Verifica se a palavra é válida.
    // Parâmetros:
    // - palavra: String contendo a palavra.
    // Retorna:
    // - true se a palavra for composta apenas por letras e espaços, com tamanho entre 3 e 15 caracteres.
    // - false caso contrário.
    public static boolean ehPalavraValida(String palavra) {
        return palavra != null && palavra.matches("^[\\p{L} ]{3,15}$"); // Regex para validar letras (incluindo acentos) e espaços.
    }
}