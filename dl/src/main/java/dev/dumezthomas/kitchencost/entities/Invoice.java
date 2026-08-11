package dev.dumezthomas.kitchencost.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class Invoice extends RestaurantScoped {

    @Column(nullable = false, length = 100)
    private String supplierName;

    @Column(nullable = false, length = 100)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDate invoiceDate;
}
