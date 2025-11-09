package hse.bank.repository.impl;

import hse.bank.domain.Operation;
import hse.bank.repository.OperationRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OperationRepositoryImpl implements OperationRepository {
    private final Map<Long, Operation> storage = new ConcurrentHashMap<>();

    @Override
    public Operation save(Operation operation) {
        storage.put(operation.getId(), operation);
        return operation;
    }

    @Override
    public Optional<Operation> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Operation> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(Operation::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> findByDateBetween(LocalDate from, LocalDate to) {
        return storage.values().stream()
                .filter(op -> !op.getDate().isBefore(from) && !op.getDate().isAfter(to))
                .sorted(Comparator.comparing(Operation::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}