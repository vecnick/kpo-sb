package hse.kpo.interfaces;

import hse.kpo.domain.Car;

public interface ICarFactory<TParams> {
    Car create(TParams carParams, int carNumber);
}
