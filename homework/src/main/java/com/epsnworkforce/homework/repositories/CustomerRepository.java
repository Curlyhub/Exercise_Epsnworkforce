package com.epsnworkforce.homework.repositories;

import java.util.List;

import com.epsnworkforce.homework.models.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByName(String name);

    Customer getOne(Long id);

    List<Customer> findCustumerById(Long id);
}
