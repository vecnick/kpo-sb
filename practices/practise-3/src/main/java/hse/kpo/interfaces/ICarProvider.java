package hse.kpo.interfaces;

import hse.kpo.domain.Car;
import hse.kpo.domain.Customer;

public interface ICarProvider {

    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
