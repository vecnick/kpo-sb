package hse.kpo.factories.cars;

import hse.kpo.domain.Car;
import hse.kpo.domain.LevitationEngine;
import hse.kpo.interfaces.ICarFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

@Component
public class LevitationCarFactory implements ICarFactory<EmptyEngineParams> {
    @Override
    public Car create(EmptyEngineParams carParams, int carNumber) {
        var engine = new LevitationEngine(); // Создаем двигатель без каких-либо параметров

        return new Car(carNumber, engine); // создаем автомобиль с ручным приводом
    }
}
