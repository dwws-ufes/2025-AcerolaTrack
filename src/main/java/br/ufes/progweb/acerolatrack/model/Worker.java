package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Worker extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //provavelmente tem que mudar de estrategia depois
    private Long id;
}
