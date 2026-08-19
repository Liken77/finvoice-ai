package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.InvalidTransactionException;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input) {
        if (input == null) {
            throw new InvalidTransactionException("Os dados da transação são obrigatórios");
        }

        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));

        log.info("Transaction persisted: id={}, category={}, amountInCents={}",
                transaction.getId().uuid(), transaction.getCategory(), transaction.getAmount());

        return TransactionOutput.from(transaction);
    }
}
