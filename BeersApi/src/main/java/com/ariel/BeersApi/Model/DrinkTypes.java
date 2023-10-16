package com.ariel.BeersApi.Model;

import com.ariel.BeersApi.Abstractions.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Table(name = "drinks_types")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name="drink_types_id"))
})
@Entity()
@Data
public class DrinkTypes extends BaseEntity {
    @Column(unique = true)
    private String description;

    @OneToMany(mappedBy = "drinkType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Drinks> drinks;

    public DrinkTypes(String description){
        this.description = description;
    }
}
