package com.beersAPI.beers.Model;

import com.beersAPI.beers.Enumerator.BeerType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public BeerType type;
    public Integer rate;
}
