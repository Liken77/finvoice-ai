package dio.budgeting.application.output;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public record TransactionSummaryOutput(int transactionCount,
                                       double total,
                                       List<CategorySummaryOutput> categories) {
    public TransactionSummaryOutput {
        categories = List.copyOf(categories);
    }

    public static TransactionSummaryOutput from(List<Transaction> transactions) {
        var totalsByCategory = new EnumMap<Category, Long>(Category.class);
        long total = 0;

        for (var transaction : transactions) {
            total = Math.addExact(total, transaction.getAmount());
            totalsByCategory.merge(transaction.getCategory(), transaction.getAmount(), Math::addExact);
        }

        var categories = Arrays.stream(Category.values())
                .filter(totalsByCategory::containsKey)
                .map(category -> new CategorySummaryOutput(
                        category.name(),
                        toValue(totalsByCategory.get(category))))
                .toList();

        return new TransactionSummaryOutput(transactions.size(), toValue(total), categories);
    }

    private static double toValue(long amount) {
        return BigDecimal.valueOf(amount, 2).doubleValue();
    }
}
