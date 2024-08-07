package com.codingtest.client.model;

import com.codingtest.client.jpa.converter.CharToBooleanConverter;
import jakarta.persistence.*;
import lombok.Data;

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

    @Column(name = "estado")
    @Convert(converter = CharToBooleanConverter.class)
    private Boolean status;
}
