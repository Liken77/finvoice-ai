package dio.budgeting.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {
    @Test
    void shouldCreateTransaction_whenDataIsValid() {
        var transaction = new Transaction("  Compra no mercado  ", 7050, Category.GROCERIES);

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getDescription()).isEqualTo("Compra no mercado");
        assertThat(transaction.getAmount()).isEqualTo(7050);
        assertThat(transaction.getCategory()).isEqualTo(Category.GROCERIES);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -5000})
    void shouldRejectTransaction_whenAmountIsNotPositive(long amount) {
        assertThatThrownBy(() -> new Transaction("Compra", amount, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("O valor da transação deve ser maior que zero");
    }

    @Test
    void shouldRejectTransaction_whenDescriptionIsBlank() {
        assertThatThrownBy(() -> new Transaction("   ", 1000, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A descrição da transação é obrigatória");
    }

    @Test
    void shouldRejectTransaction_whenDescriptionExceedsLimit() {
        assertThatThrownBy(() -> new Transaction("a".repeat(256), 1000, Category.GROCERIES))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A descrição deve ter no máximo 255 caracteres");
    }

    @Test
    void shouldRejectTransaction_whenCategoryIsNull() {
        assertThatThrownBy(() -> new Transaction("Compra", 1000, null))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessage("A categoria da transação é obrigatória");
    }
}
