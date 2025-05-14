package exceptions;

// Classe personalizada para representar exceções relacionadas a repositórios.
// Estende a classe Exception, tornando-a uma exceção verificada (checked exception).
public class RepositoryException extends Exception {

    // Construtor que aceita apenas uma mensagem de erro.
    // Parâmetros:
    // - message: String contendo a descrição do erro.
    public RepositoryException(String message) {
        super(message); // Chama o construtor da classe base (Exception) com a mensagem fornecida.
    }

    // Construtor que aceita uma mensagem de erro e uma causa.
    // Parâmetros:
    // - message: String contendo a descrição do erro.
    // - cause: Throwable que representa a causa original do erro.
    public RepositoryException(String message, Throwable cause) {
        super(message, cause); // Chama o construtor da classe base (Exception) com a mensagem e a causa fornecidas.
    }
}