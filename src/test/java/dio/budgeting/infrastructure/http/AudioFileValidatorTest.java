package dio.budgeting.infrastructure.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class AudioFileValidatorTest {
    private AudioFileValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AudioFileValidator();
    }

    @Test
    void shouldAcceptSupportedAudioFile() {
        var file = new MockMultipartFile(
                "file", "expense.M4A", "audio/mp4", new byte[]{1, 2, 3});

        assertThatCode(() -> validator.validate(file)).doesNotThrowAnyException();
    }

    @Test
    void shouldRejectEmptyFile() {
        var file = new MockMultipartFile("file", "expense.mp3", "audio/mpeg", new byte[0]);

        assertThatThrownBy(() -> validator.validate(file))
                .isInstanceOf(InvalidAudioFileException.class)
                .hasMessage("O arquivo de áudio é obrigatório");
    }

    @Test
    void shouldRejectUnsupportedFileFormat() {
        var file = new MockMultipartFile("file", "notes.txt", "text/plain", new byte[]{1});

        assertThatThrownBy(() -> validator.validate(file))
                .isInstanceOf(InvalidAudioFileException.class)
                .hasMessageContaining("Formato de áudio inválido");
    }

    @Test
    void shouldRejectFileLargerThanLimit() {
        var file = new MockMultipartFile(
                "file", "expense.mp3", "audio/mpeg", new byte[]{1}) {
            @Override
            public long getSize() {
                return 25L * 1024 * 1024 + 1;
            }
        };

        assertThatThrownBy(() -> validator.validate(file))
                .isInstanceOf(InvalidAudioFileException.class)
                .hasMessage("O arquivo de áudio deve ter no máximo 25 MB");
    }
}
