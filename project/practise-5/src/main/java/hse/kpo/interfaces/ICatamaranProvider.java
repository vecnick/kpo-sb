package hse.kpo.interfaces;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;

public interface ICatamaranProvider {
    Catamaran takeCatamaran(Customer customer);
}
