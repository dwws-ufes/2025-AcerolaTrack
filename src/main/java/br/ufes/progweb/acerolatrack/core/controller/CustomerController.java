package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.service.IManageCustomerService;
import br.ufes.progweb.acerolatrack.model.Customer;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Endpoint
@AnonymousAllowed
@PermitAll
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

    @GetMapping
    public Page<Customer> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return manageCustomerService.getAllCustomers(pageRequest);
    }

    @PatchMapping("/{id}")
    public Customer updateCustomerName(
            @PathVariable Long id,
            @RequestBody String name) {
        log.info("Updating customer name. ID: {}, New name: {}", id, name);
        return manageCustomerService.updateCustomerName(id, name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        log.info("Deactivating customer with id: {}", id);
        manageCustomerService.deleteCustomer(id);
    }
}
