package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.service.IManageCustomerService;
import br.ufes.progweb.acerolatrack.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/customers")
public class CustomerController {

    private final IManageCustomerService manageCustomerService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        log.info("Creating customer: {}", customer.getName());
        // Logic to create a customer would go here
        return manageCustomerService.saveCustomer(customer);
    }
}
