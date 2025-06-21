package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
