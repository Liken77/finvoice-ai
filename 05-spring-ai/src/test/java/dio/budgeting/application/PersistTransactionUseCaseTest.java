package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.InvalidTransactionException;
import dio.budgeting.support.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class PersistTransactionUseCaseTest {
    private InMemoryTransactionRepository transactionRepository;
    private PersistTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        transactionRepository = new InMemoryTransactionRepository();
        useCase = new PersistTransactionUseCase(transactionRepository);
    }

    @Test
    void shouldPersistTransactionAndConvertCentsToReais() {
        var output = useCase.execute(
                new PersistTransactionInput("Remédio", 4590, Category.PHARMA));

        assertThat(output.description()).isEqualTo("Remédio");
        assertThat(output.category()).isEqualTo("PHARMA");
        assertThat(output.value()).isEqualTo(45.90);
        assertThat(transactionRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldNotCallRepository_whenTransactionIsInvalid() {
        var input = new PersistTransactionInput("Combustível", 0, Category.AUTO);

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(InvalidTransactionException.class);

        assertThat(transactionRepository.findAll()).isEmpty();
    }
}
