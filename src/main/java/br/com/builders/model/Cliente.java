package br.com.builders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.nonNull;

@Data
@Builder
@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpfCnpj;
    private LocalDate dataNascimento;
    @Embedded
    private Endereco endereco;

    public int getIdade() {
        if(nonNull(dataNascimento)) {
            return Period.between(dataNascimento, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

}

