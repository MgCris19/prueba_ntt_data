package com.codingtest.account.model;

import com.codingtest.account.converter.CharToBooleanConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_persona")
    private Person person;

    @Column(name = "contrasena")
    private String password;

    @Convert(converter = CharToBooleanConverter.class)
    @Column(name = "estado")
    private Boolean status;

}
