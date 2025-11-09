package hse.finance.repository;

import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OperationRepository extends Repository<Operation> {
    List<Operation> findByBankAccountId(UUID bankAccountId);
    List<Operation> findByCategoryId(UUID categoryId);
    List<Operation> findByDateRange(LocalDate from, LocalDate to);
    List<Operation> findByType(OperationType type);
}

