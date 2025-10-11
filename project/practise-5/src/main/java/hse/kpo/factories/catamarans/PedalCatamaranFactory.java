package hse.kpo.factories.catamarans;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.ICatamaranFactory;
import hse.kpo.params.PedalEngineParams;

public class PedalCatamaranFactory implements ICatamaranFactory<PedalEngineParams> {
    @Override
    public Catamaran create(PedalEngineParams catamaranParams, int catamaranNumber) {
        var engine = new PedalEngine(catamaranParams.pedalSize());

        return new Catamaran(catamaranNumber, engine);
    }
}
