package com.codingtest.account.entity;

import com.codingtest.account.mapper.CharToBooleanConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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