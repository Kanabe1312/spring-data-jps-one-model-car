package app.car;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(
        name = "cars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"brand", "model"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Brand is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String model;

    @Min(value = 1985, message = "Year must be at least 1985")
    private int year;

    @NotBlank(message = "Color is required")
    private String color;

    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private double price;


    private LocalDate registrationDate;

    private boolean available;
    public Car(String brand, String model, int year, String color, double price, LocalDate registrationDate, boolean available) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
        this.registrationDate = registrationDate;
        this.available = available;
    }
}
