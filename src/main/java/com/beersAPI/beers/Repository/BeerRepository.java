package com.beersAPI.beers.Repository;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Beer findByName(String name);

    List<Beer> findByRate(int rate);

    List<Beer> findByType(BeerType type);

    @Modifying
    @Query("update Beer b set b.rate = ?1 where b.id = ?2")
    void setBeerRateById(int rate, Long id);
}
