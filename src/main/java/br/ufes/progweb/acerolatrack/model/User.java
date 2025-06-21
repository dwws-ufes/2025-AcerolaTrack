package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public abstract class User {

    private String username;
    private String password;
    private String email;

}
