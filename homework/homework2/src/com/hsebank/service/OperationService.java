package com.hsebank.service;

import com.hsebank.domain.Operation;
import com.hsebank.factory.DomainFactory;
import com.hsebank.repository.OperationRepository;
import com.hsebank.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public final class OperationService {
    private final OperationRepository repo;
    private final BankAccountRepository bankRepo;
    private final DomainFactory factory;

    public OperationService(OperationRepository repo, BankAccountRepository bankRepo, DomainFactory factory) {
        this.repo = repo;
        this.bankRepo = bankRepo;
        this.factory = factory;
    }

    public Operation create(Operation.Type type, String bankAccountId, String categoryId, BigDecimal amount, LocalDate date, String description) {
        if (!bankRepo.findById(bankAccountId).isPresent()) throw new IllegalArgumentException("account not found");
        Operation op = factory.createOperation(type, bankAccountId, categoryId, amount, date, description);
        repo.save(op);
        BigDecimal delta = type == Operation.Type.INCOME ? amount : amount.negate();
        bankRepo.findById(bankAccountId).ifPresent(acc -> {
            acc.applyDelta(delta);
            bankRepo.save(acc);
        });
        return op;
    }

    public Operation addOperation(Operation.Type type, String bankAccountId, String categoryId, BigDecimal amount, LocalDate date, String description) {
        return create(type, bankAccountId, categoryId, amount, date, description);
    }

    public void updateOperation(String id, Operation.Type type, String bankAccountId, String categoryId, BigDecimal amount, LocalDate date, String description) {
        Optional<Operation> oldOpt = repo.findById(id);
        if (oldOpt.isPresent()) {
            Operation old = oldOpt.get();
            BigDecimal revert = old.getType() == Operation.Type.INCOME ? old.getAmount().negate() : old.getAmount();
            bankRepo.findById(old.getBankAccountId()).ifPresent(acc -> {
                acc.applyDelta(revert);
                bankRepo.save(acc);
            });
            repo.delete(id);
        }
        Operation created = factory.createOperation(type, bankAccountId, categoryId, amount, date, description);
        repo.save(created);
        BigDecimal delta = type == Operation.Type.INCOME ? amount : amount.negate();
        bankRepo.findById(bankAccountId).ifPresent(acc -> {
            acc.applyDelta(delta);
            bankRepo.save(acc);
        });
    }

    public Optional<Operation> find(String id) { return repo.findById(id); }
    public Collection<Operation> list() { return repo.findAll(); }
    public void delete(String id) {
        repo.findById(id).ifPresent(op -> {
            BigDecimal delta = op.getType() == Operation.Type.INCOME ? op.getAmount().negate() : op.getAmount();
            bankRepo.findById(op.getBankAccountId()).ifPresent(acc -> {
                acc.applyDelta(delta);
                bankRepo.save(acc);
            });
            repo.delete(id);
        });
    }
}
