package hse.kpo.interfaces;

import hse.kpo.domain.Car;

public interface IShipFactory<TParams> {
    Car create(TParams carParams, int carNumber);
}
