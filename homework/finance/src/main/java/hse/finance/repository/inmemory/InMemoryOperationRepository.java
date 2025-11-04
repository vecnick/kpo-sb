package hse.finance.repository.inmemory;

import hse.finance.domain.Operation;
import hse.finance.domain.OperationType;
import hse.finance.repository.OperationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryOperationRepository extends AbstractInMemoryRepository<Operation> implements OperationRepository {
    @Override
    public List<Operation> findByBankAccountId(UUID bankAccountId) {
        return storage.values().stream().filter(o -> o.getBankAccountId().equals(bankAccountId)).collect(Collectors.toList());
    }

    @Override
    public List<Operation> findByCategoryId(UUID categoryId) {
        return storage.values().stream().filter(o -> o.getCategoryId().equals(categoryId)).collect(Collectors.toList());
    }

    @Override
    public List<Operation> findByDateRange(LocalDate from, LocalDate to) {
        return storage.values().stream().filter(o -> !o.getDate().isBefore(from) && !o.getDate().isAfter(to)).collect(Collectors.toList());
    }

    @Override
    public List<Operation> findByType(OperationType type) {
        return storage.values().stream().filter(o -> o.getType() == type).collect(Collectors.toList());
    }
}

