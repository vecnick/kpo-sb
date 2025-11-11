package hse_bank.finance.proxy;

import hse_bank.finance.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryProxy<T> implements Repository<T> {
    private final Repository<T> realRepository;
    private final Repository<T> cacheRepository;
    private boolean cacheInitialized = false;

    public RepositoryProxy(Repository<T> realRepository, Repository<T> cacheRepository) {
        this.realRepository = realRepository;
        this.cacheRepository = cacheRepository;
    }

    private void initializeCache() {
        if (!cacheInitialized) {
            realRepository.findAll().forEach(cacheRepository::save);
            cacheInitialized = true;
        }
    }

    @Override
    public void save(T entity) {
        realRepository.save(entity);
        cacheRepository.save(entity);
    }

    @Override
    public Optional<T> findById(UUID id) {
        initializeCache();
        Optional<T> result = cacheRepository.findById(id);
        if (result.isEmpty()) {
            result = realRepository.findById(id);
            result.ifPresent(cacheRepository::save);
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        initializeCache();
        return cacheRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        realRepository.delete(id);
        cacheRepository.delete(id);
    }
}