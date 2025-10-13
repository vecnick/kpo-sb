package hse.kpo.interfaces;

import hse.kpo.domain.Car;

public interface ICarFactory<TParams> {
    Car createCar(TParams carParams, int carNumber);
}
