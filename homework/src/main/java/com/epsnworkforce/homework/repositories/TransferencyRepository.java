package com.epsnworkforce.homework.repositories;
import com.epsnworkforce.homework.models.Transferency;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferencyRepository extends 
        org.springframework.data.jpa.repository.JpaRepository<Transferency, Long> {

}
