package dio.budgeting.infrastructure.http;

public class InvalidAudioFileException extends RuntimeException {
    public InvalidAudioFileException(String message) {
        super(message);
    }
}
