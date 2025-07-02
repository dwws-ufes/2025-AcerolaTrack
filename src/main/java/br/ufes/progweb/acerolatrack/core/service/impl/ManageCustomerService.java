package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.repository.CustomerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageCustomerService;
import br.ufes.progweb.acerolatrack.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageCustomerService implements IManageCustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer updateCustomerName(Long id, String name) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(name);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customer.setActive(false);
        customerRepository.save(customer);
    }
}
