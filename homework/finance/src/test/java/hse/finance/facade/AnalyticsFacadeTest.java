package hse.finance.facade;

import hse.finance.domain.*;
import hse.finance.repository.inmemory.InMemoryBankAccountRepository;
import hse.finance.repository.inmemory.InMemoryCategoryRepository;
import hse.finance.repository.inmemory.InMemoryOperationRepository;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsFacadeTest {

    @Test
    void incomeMinusExpense_and_totalByCategory_work() {
        var accRepo = new InMemoryBankAccountRepository();
        var catRepo = new InMemoryCategoryRepository();
        var opRepo = new InMemoryOperationRepository();
        var factory = new Factory();

        var acc = accRepo.save(factory.createBankAccount("Main account", BigDecimal.ZERO));
        var cIncome = catRepo.save(factory.createCategory(CategoryType.INCOME, "Salary"));
        var cExpense = catRepo.save(factory.createCategory(CategoryType.EXPENSE, "Cafe"));

        opRepo.save(factory.createOperation(OperationType.INCOME, acc.getId(), new BigDecimal("1000"), LocalDate.now(), null, cIncome.getId()));
        opRepo.save(factory.createOperation(OperationType.EXPENSE, acc.getId(), new BigDecimal("200"), LocalDate.now(), null, cExpense.getId()));
        opRepo.save(factory.createOperation(OperationType.EXPENSE, acc.getId(), new BigDecimal("300"), LocalDate.now(), null, cExpense.getId()));

        var analytics = new AnalyticsFacade(opRepo, catRepo);
        var from = LocalDate.now().minusDays(1);
        var to = LocalDate.now().plusDays(1);

        assertEquals(new BigDecimal("500"), analytics.incomeMinusExpense(from, to));
        var byCat = analytics.totalByCategory(from, to);
        assertEquals(new BigDecimal("1000"), byCat.get(cIncome));
        assertEquals(new BigDecimal("500"), byCat.get(cExpense));
    }
}
