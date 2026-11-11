package app;

import app.car.Car;
import app.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
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


        carRepository.findAll().stream().forEach(System.out::println);
        populeaza();
        citeste();
    }

    public void populeaza(){
        carRepository.deleteAll();

        carRepository.saveAll(List.of(
                new Car("Audi","A7",2005,"Black",15.000),
                new Car("BMW","I8",2014,"White",25.000),
                new Car("Mercedes", "C63", 2018, "Grey", 35.000),
                new Car("Porsche", "911", 2020, "Red", 85.000),
                new Car("Volkswagen", "Golf GTI", 2019, "Blue", 22.000),
                new Car("Ferrari", "488", 2017, "Yellow", 120.000),
                new Car("Lamborghini", "Huracan", 2021, "Green", 180.000),
                new Car("Tesla", "Model S", 2022, "White", 75.000),
                new Car("Toyota", "Supra", 2020, "Orange", 55.000),
                new Car("Audi", "RS6", 2023, "Black", 95.000)
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
}
