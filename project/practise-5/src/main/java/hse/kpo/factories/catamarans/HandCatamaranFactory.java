package hse.kpo.factories.catamarans;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.EmptyEngineParams;

public class HandCatamaranFactory implements ICatamaranFactory<EmptyEngineParams> {
    @Override
    public Catamaran create(EmptyEngineParams catamaranParams) {
        var engine = new HandEngine();

        return new Catamaran(engine);
    }
}
