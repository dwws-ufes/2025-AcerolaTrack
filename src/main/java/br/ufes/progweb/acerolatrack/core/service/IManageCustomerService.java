package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IManageCustomerService {

    Customer saveCustomer(Customer customer);

    Page<Customer> getAllCustomers(Pageable pageable);

}
