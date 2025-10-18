package hse.kpo.homework1;

import hse.kpo.homework1.living.Monkey;
import hse.kpo.homework1.living.Rabbit;
import hse.kpo.homework1.living.Tiger;
import hse.kpo.homework1.living.Wolf;
import hse.kpo.homework1.inventory.Table;
import hse.kpo.homework1.inventory.Computer;
import hse.kpo.homework1.service.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;

@SpringBootApplication
public class ZooApp {
    public static void main(String[] args) {
        SpringApplication.run(ZooApp.class, args);
    }

    @Bean
    CommandLineRunner run(@Autowired ZooService zooService) {
        return args -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("Zoo Management Console Application стартовал.\n");
            boolean run = true;
            // demo инвентарь
            zooService.addThing(new Table("Овальный", 1001));
            zooService.addThing(new Computer("Lenovo ZooPC", 1452));
            while (run) {
                System.out.println("1. Добавить животное (Monkey/Tiger/Rabbit/Wolf)\n2. Вывести отчёт по корму\n3. Список для контактного зоопарка\n4. Вывести инвентарные номера\n0. Выход");
                String input = sc.nextLine();
                switch (input) {
                    case "1":
                        System.out.println("Введите тип (monkey/tiger/rabbit/wolf):");
                        String type = sc.nextLine();
                        System.out.println("Имя:");
                        String name = sc.nextLine();
                        System.out.println("Потребление корма, кг/день:");
                        int food = Integer.parseInt(sc.nextLine());
                        System.out.println("Инвентарный номер:");
                        int num = Integer.parseInt(sc.nextLine());
                        switch(type.toLowerCase()) {
                            case "monkey":
                                System.out.println("Доброта (0-10):");
                                int kindnessM = Integer.parseInt(sc.nextLine());
                                zooService.addAnimal(new Monkey(name, food, num, kindnessM));
                                break;
                            case "rabbit":
                                System.out.println("Доброта (0-10):");
                                int kindnessR = Integer.parseInt(sc.nextLine());
                                zooService.addAnimal(new Rabbit(name, food, num, kindnessR));
                                break;
                            case "tiger":
                                zooService.addAnimal(new Tiger(name, food, num));
                                break;
                            case "wolf":
                                zooService.addAnimal(new Wolf(name, food, num));
                                break;
                            default:
                                System.out.println("Неизвестный тип");
                        }
                        break;
                    case "2":
                        System.out.println("Всего нужно корма: " + zooService.getTotalFood() + " кг/день");
                        break;
                    case "3":
                        System.out.println("Для контактного зоопарка подходят:");
                        zooService.getContactAnimals().forEach(a -> System.out.println(a.getName()));
                        break;
                    case "4":
                        System.out.println("Инвентарные номера:");
                        zooService.getInventoryReport().forEach(item -> System.out.println(item.getClass().getSimpleName() + ": " + (item instanceof hse.kpo.homework1.living.Animal an ? an.getName() : ((hse.kpo.homework1.inventory.Thing)item).getName()) + ", №" + item.getNumber()));
                        break;
                    case "0":
                        run = false;
                        break;
                    default:
                        System.out.println("Неизвестная команда");
                }
            }
            System.out.println("Работа завершена.");
        };
    }
}
