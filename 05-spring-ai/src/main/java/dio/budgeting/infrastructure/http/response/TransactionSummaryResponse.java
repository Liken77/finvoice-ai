package dio.budgeting.infrastructure.http.response;

import dio.budgeting.application.output.CategorySummaryOutput;
import dio.budgeting.application.output.TransactionSummaryOutput;

import java.util.List;

public record TransactionSummaryResponse(int transactionCount,
                                         double total,
                                         List<CategorySummaryResponse> categories) {
    public static TransactionSummaryResponse from(TransactionSummaryOutput output) {
        return new TransactionSummaryResponse(
                output.transactionCount(),
                output.total(),
                output.categories().stream().map(CategorySummaryResponse::from).toList());
    }

    public record CategorySummaryResponse(String category, double total) {
        private static CategorySummaryResponse from(CategorySummaryOutput output) {
            return new CategorySummaryResponse(output.category(), output.total());
        }
    }
}
