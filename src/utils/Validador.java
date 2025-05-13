package utils;

public class Validador {
    public static boolean ehLetraValida(String input) {
        return input != null && input.length() == 1 &&
                Character.isLetter(input.charAt(0));
    }

    public static boolean ehTemaValido(String nome) {
        return nome != null && nome.matches("^[\\p{L} ]{3,20}$");
    }

    public static boolean ehPalavraValida(String palavra) {
        return palavra != null && palavra.matches("^[\\p{L} ]{3,15}$");
    }
}