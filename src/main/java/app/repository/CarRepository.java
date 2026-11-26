package app.repository;

import app.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    //EX4
    List<Car> findByBrand(String brand);
    boolean existsByBrand(String brand);
    List<Car> findByColor(String color);
    long countByBrand(String brand);
    //EX5
    List<Car> findByBrandContainingIgnoreCase(String brand);
    List<Car> findByPriceBetween(double min, double max);
    List<Car> findByYearLessThan(int year);
    List<Car> findByPriceGreaterThanEqual(double price);
    List<Car> findByModelContainingIgnoreCase(String model);
    List<Car> findByColorIn(List<String> color);
    List<Car> findByBrandAndColor(String brand, String color);



    // OR
    List<Car> findByBrandOrColor(String brand, String color);

    // BEFORE / AFTER
    List<Car> findByRegistrationDateBefore(LocalDate date);
    List<Car> findByRegistrationDateAfter(LocalDate date);

    // TRUE / FALSE
    List<Car> findByAvailableTrue();
    List<Car> findByAvailableFalse();


}
