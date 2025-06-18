package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //provavelmente tem que mudar de estrategia depois
    private Long id;
    private String username;
    private String password;
    private String email;

}
