package app.repository;

import app.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    //EX4
    List<Car> findByBrand(String Brand);
    boolean existsByBrand(String Brand);
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


}
