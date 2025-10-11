package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.IEngine;
import lombok.ToString;

@ToString
public class Catamaran {
    IEngine engine;
    int number;

    public Catamaran(int number, IEngine engine) {
        this.engine = engine;
        this.number = number;
    }

    public boolean isCompatible(Customer customer) {
        boolean isStrong = customer.getLegPower() > 2 || customer.getHandPower() > 2;
        boolean isClever = customer.getIq() > 200;

        return this.engine.isCompatible(customer, ProductionTypes.CATAMARAN) && (isStrong || isClever);
    }
}
