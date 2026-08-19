package dio.budgeting.infrastructure.http;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.Set;

@Component
public class AudioFileValidator {
    private static final long MAX_FILE_SIZE = 25L * 1024 * 1024;
    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(
            "mp3", "mp4", "mpeg", "mpga", "m4a", "wav", "webm");

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidAudioFileException("O arquivo de áudio é obrigatório");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidAudioFileException("O arquivo de áudio deve ter no máximo 25 MB");
        }

        var extension = getExtension(file.getOriginalFilename());
        if (!SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new InvalidAudioFileException(
                    "Formato de áudio inválido. Use mp3, mp4, mpeg, mpga, m4a, wav ou webm");
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int separatorIndex = fileName.lastIndexOf('.');
        if (separatorIndex < 0 || separatorIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(separatorIndex + 1).toLowerCase(Locale.ROOT);
    }
}
