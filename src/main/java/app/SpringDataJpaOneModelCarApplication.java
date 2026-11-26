package app;

import app.car.Car;
import app.repository.CarRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SpringDataJpaOneModelCarApplication  implements CommandLineRunner {
    @Autowired
    private CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaOneModelCarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populeaza();
        citeste();
        valideaza();
        metodeDerivate();
        metodeDerivateSingur();
    }

    public void populeaza(){
        carRepository.deleteAll();

        carRepository.saveAll(List.of(
                new Car("Audi", "A7", 2005, "Black", 15000, LocalDate.of(2020, 5, 10), true),
                new Car("BMW", "I8", 2014, "White", 25000, LocalDate.of(2019, 3, 15), false),
                new Car("Mercedes", "C63", 2018, "Grey", 35000, LocalDate.of(2021, 7, 20), true),
                new Car("Porsche", "911", 2020, "Red", 85000, LocalDate.of(2022, 1, 12), true),
                new Car("Volkswagen", "Golf GTI", 2019, "Blue", 22000, LocalDate.of(2020, 9, 5), false),
                new Car("Ferrari", "488", 2017, "Yellow", 120000, LocalDate.of(2018, 4, 18), true),
                new Car("Lamborghini", "Huracan", 2021, "Green", 180000, LocalDate.of(2023, 2, 8), false),
                new Car("Tesla", "Model S", 2022, "White", 75000, LocalDate.of(2022, 11, 25), true),
                new Car("Toyota", "Supra", 2020, "Orange", 55000, LocalDate.of(2021, 6, 30), true),
                new Car("Audi", "RS6", 2023, "Black", 95000, LocalDate.of(2024, 1, 15), false)
        ));

        System.out.println("Masini: " + carRepository.count() );

    }

    private void citeste(){
        List<Car> masini = carRepository.findAll();

        masini.forEach(System.out :: println);

        UUID primulId = masini.get(0).getId();

        Optional<Car> gasit = carRepository.findById(primulId);
        gasit.ifPresent(car -> System.out.println("Gasit: " + car.getBrand()+ " " + car.getModel()));

        UUID idInexistent = UUID.fromString("00000000-0000-0000-0000-000000000000");

        Optional<Car> inexistent = carRepository.findById(idInexistent);
        System.out.println("Exista: " + carRepository.existsById(idInexistent));

        if(inexistent.isEmpty()){
            System.out.println("Masina inexistenta");
        }
    }

    private void valideaza() {
        Car gresit = new Car(
                " ", " ", 1800, "", -100, LocalDate.now(), true);
        try {
            carRepository.saveAndFlush(gresit);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage()).sorted()
                    .forEach(System.out::println);
        }

        Car duplicat = new Car("Audi", "A7", 2005, "Black", 15000, LocalDate.of(2020, 5, 10), true);

        try {
            carRepository.saveAndFlush(duplicat);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Car already used: Audi A7");

        }
        System.out.println("Masini: " + carRepository.count());
    }


    private void metodeDerivate() {

        carRepository.findByBrand("Audi").forEach(car -> System.out.println(car.getBrand() + " " + car.getModel()));

        System.out.println("Bugatti not found: " + carRepository.findByBrand("Bugatti").isEmpty());

        System.out.println("BMW exists: " + carRepository.existsByBrand("BMW"));

        carRepository.findByColor("Black").forEach(car -> System.out.println(car.getBrand() + " " + car.getModel()));

        System.out.println("Audi count: " + carRepository.countByBrand("Audi"));
    }


    private void metodeDerivateSingur(){
        System.out.println("\n--- Brand Contains A ---");
        carRepository.findByBrandContainingIgnoreCase("a").forEach(car -> System.out.println(car.getBrand()));

        System.out.println("\n--- Price between 20000 and 50000 ---");
        carRepository.findByPriceBetween(20000, 50000).forEach(car -> System.out.println(car.getBrand() + " " + car.getModel()));

        System.out.println("\n--- Year less than 2020 ---");
        carRepository.findByYearLessThan(2020).forEach(car -> System.out.println(car.getBrand() + " " + car.getYear()));

        System.out.println("\n--- Price greater than or equal 50000 ---");
        carRepository.findByPriceGreaterThanEqual(50000).forEach(car-> System.out.println(car.getBrand()));

        System.out.println("\n--- Model contains A ---");
        carRepository.findByModelContainingIgnoreCase("a").forEach(car-> System.out.println(car.getModel()));

        System.out.println("\n--- Black or White cars ---");
        carRepository.findByColorIn(List.of("Black","White")).forEach(car -> System.out.println(car.getBrand() +" "+ car.getColor()));

        System.out.println("\n--- BMW and White ---");
        carRepository.findByBrandAndColor("BMW","White").forEach(car -> System.out.println(car.getBrand() + " " + car.getColor()));

        System.out.println("\n--- Brand Audi OR Color White ---");
        carRepository.findByBrandOrColor("Audi", "White").forEach(car -> System.out.println(car.getBrand() + " " + car.getColor()));

        System.out.println("\n--- Registered before 2021 ---");
        carRepository.findByRegistrationDateBefore(LocalDate.of(2021, 1, 1)).forEach(car -> System.out.println(car.getBrand() + " " + car.getRegistrationDate()));

        System.out.println("\n--- Registered after 2021 ---");
        carRepository.findByRegistrationDateAfter(LocalDate.of(2021, 1, 1)).forEach(car -> System.out.println(car.getBrand() + " " + car.getRegistrationDate()));

        System.out.println("\n--- Available cars ---");
        carRepository.findByAvailableTrue().forEach(car -> System.out.println(car.getBrand() + " " + car.getModel()));

        System.out.println("\n--- Unavailable cars ---");
        carRepository.findByAvailableFalse().forEach(car -> System.out.println(car.getBrand() + " " + car.getModel()));
    }

}
