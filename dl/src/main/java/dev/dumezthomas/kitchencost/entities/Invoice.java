package dev.dumezthomas.kitchencost.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_invoice_restaurant", columnList = "restaurant_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"supplierName", "invoiceNumber"})
        })
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class Invoice extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String supplierName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDate invoiceDate;
}
