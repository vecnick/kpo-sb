package hse.kpo.domain.inventory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Computer extends Thing {
    private String processor;

    public Computer(String name, int number, String processor) {
        super(name, number);
        this.processor = processor;
    }

}