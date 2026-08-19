package dio.budgeting.application;

import dio.budgeting.application.output.CategorySummaryOutput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.support.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetTransactionSummaryUseCaseTest {
    private InMemoryTransactionRepository transactionRepository;
    private GetTransactionSummaryUseCase useCase;

    @BeforeEach
    void setUp() {
        transactionRepository = new InMemoryTransactionRepository();
        useCase = new GetTransactionSummaryUseCase(transactionRepository);
    }

    @Test
    void shouldCalculateTotalAndGroupTransactionsByCategory() {
        transactionRepository.addAll(
                new Transaction("Mercado", 5000, Category.GROCERIES),
                new Transaction("Padaria", 2500, Category.GROCERIES),
                new Transaction("Farmácia", 1800, Category.PHARMA),
                new Transaction("Combustível", 3050, Category.AUTO));

        var output = useCase.execute();

        assertThat(output.transactionCount()).isEqualTo(4);
        assertThat(output.total()).isEqualTo(123.50);
        assertThat(output.categories()).containsExactly(
                new CategorySummaryOutput("GROCERIES", 75.00),
                new CategorySummaryOutput("PHARMA", 18.00),
                new CategorySummaryOutput("AUTO", 30.50));
    }

    @Test
    void shouldReturnEmptySummary_whenThereAreNoTransactions() {
        var output = useCase.execute();

        assertThat(output.transactionCount()).isZero();
        assertThat(output.total()).isZero();
        assertThat(output.categories()).isEmpty();
    }
}
