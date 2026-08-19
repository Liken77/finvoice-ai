package dio.budgeting.application;

import dio.budgeting.application.output.TransactionSummaryOutput;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class GetTransactionSummaryUseCase {
    private final TransactionRepository transactionRepository;

    public GetTransactionSummaryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "get-transaction-summary",
            description = "Calcula o total de gastos e os totais agrupados por categoria")
    public TransactionSummaryOutput execute() {
        return TransactionSummaryOutput.from(transactionRepository.findAll());
    }
}
