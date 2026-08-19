package dio.budgeting.support;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory() == category)
                .toList();
    }

    public void addAll(Transaction... transactions) {
        this.transactions.addAll(Arrays.asList(transactions));
    }
}
