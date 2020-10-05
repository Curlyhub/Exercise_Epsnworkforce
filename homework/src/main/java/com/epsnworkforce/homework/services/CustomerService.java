package com.epsnworkforce.homework.services;

import java.io.IOException;
import java.util.List;

import com.epsnworkforce.homework.models.Customer;
import com.epsnworkforce.homework.models.Transferency;

public interface CustomerService {

    public Customer findCustumer(Long id);

    public List<Customer> getAllCustumer();

    public Customer saveCustumer(Customer member);

    public Customer updateCustumer(Customer member, Long id);

    public Boolean deleteCustumer(Long id);

    public Boolean makeTransfer(Transferency transfer) throws IOException;

    public List<Transferency>  getAllTransferencies();
    
}
