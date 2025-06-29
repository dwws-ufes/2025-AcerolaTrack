package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class User extends AuditEntity {

    private String username;
    private String password;
    @Column(unique=true)
    private String email;

}
