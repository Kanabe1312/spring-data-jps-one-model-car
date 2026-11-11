package app.repository;

import app.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    Optional<Car> findByBrand(String Brand);
    boolean existsByBrand(String Brand);
    List<Car> findByColor(String color);
    long countByBrand(String brand);

}
