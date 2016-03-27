package com.github.medhanie.spboot.ws.repository;

import com.github.medhanie.spboot.model.Person;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Medhanie on 3/27/2016.
 */
public interface PersonRepository extends JpaRepository<Person, Long>{
}
