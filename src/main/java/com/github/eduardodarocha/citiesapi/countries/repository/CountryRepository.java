package com.github.eduardodarocha.citiesapi.countries.repository;

import com.github.eduardodarocha.citiesapi.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
