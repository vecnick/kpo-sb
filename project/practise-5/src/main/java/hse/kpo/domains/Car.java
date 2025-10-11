package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Car {

    private IEngine engine;

    @Getter
    private int VIN;

    public Car(int VIN, IEngine engine) {
        this.VIN = VIN;
        this.engine = engine;
    }

    public boolean isCompatible(Customer customer) {
        boolean isStrong = customer.getLegPower() > 5 || customer.getHandPower() > 5;
        boolean isClever = customer.getIq() > 300;

        return this.engine.isCompatible(customer, ProductionTypes.CAR) && (isClever || isStrong);// внутри метода просто вызываем соответствующий метод двигателя
    }
}
