package hse.kpo.services;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.interfaces.ICatamaranProvider;

import java.util.ArrayList;
import java.util.List;

public class CatamaranStorage implements ICatamaranProvider {
    private final List<Catamaran> catamarans = new ArrayList<>();

//    private int catamaranNumberCounter = 0;

    @Override
    public Catamaran takeCatamaran(Customer customer) {

        var filteredCatamarans = catamarans.stream().filter(catamaran -> catamaran.isCompatible(customer)).toList();

        var firstCatamaran = filteredCatamarans.stream().findFirst();

        firstCatamaran.ifPresent(catamarans::remove);

        return firstCatamaran.orElse(null);
    }

    public <TParams> void addCatamaran(ICatamaranFactory<TParams> catamaranFactory, TParams catamaranParams)
    {
        // создаем автомобиль из переданной фабрики
        var catamaran = catamaranFactory.create(
                catamaranParams // передаем параметры
//                ++catamaranNumberCounter // передаем номер - номер будет начинаться с 1
        );

        catamarans.add(catamaran); // добавляем автомобиль
    }
}
