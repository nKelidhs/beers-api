package com.beersAPI.beers.Repository;

import com.beersAPI.beers.Enumerator.BeerType;
import com.beersAPI.beers.Model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Optional<Beer> findByName(String name);

    @Modifying
    @Query("update Beer b set b.rate = ?2 where b.id = ?1")
    void setBeerRateById(Long id, int rate);
}
