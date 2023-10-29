package com.ariel.BeersApi.Model;

import com.ariel.BeersApi.Abstractions.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Table(name="drinks")
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column=@Column(name="drinks_id"))
})
@Getter
@Setter
@AllArgsConstructor
public class Drinks extends BaseEntity {
    @Column
    private String name;

    @Column(name="alcohol_rate")
    private float alcoholRate;

    @Column
    private double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="drink_types_id", nullable = false)
    private DrinkTypes drinkType;
}
