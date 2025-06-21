package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.repository.CustomerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageCustomerService;
import br.ufes.progweb.acerolatrack.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageCustomerService implements IManageCustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
