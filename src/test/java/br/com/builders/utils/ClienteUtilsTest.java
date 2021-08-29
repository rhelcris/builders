package br.com.builders.utils;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.enums.TipoUF;
import br.com.builders.model.Cliente;
import br.com.builders.model.Endereco;

import java.time.LocalDate;
import java.time.Month;

public class ClienteUtilsTest {

    public static ClienteRequest umClienteRequest() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .idCliente(1l)
                .nomeCliente("Pedro Guilherme")
                .numeroDocumento("11111111111")
                .dataNascimento(LocalDate.of(1987, Month.APRIL, 8))
                .endereco(umEndereco())
                .build();
        return clienteRequest;
    }

    public static Cliente umCliente() {
        Cliente cliente = Cliente.builder()
                .id(1l)
                .nome("Pedro Guilherme")
                .cpfCnpj("11111111111")
                .dataNascimento(LocalDate.of(1987, Month.APRIL, 8))
                .endereco(umEndereco())
                .build();

        return cliente;
    }

    private static Endereco umEndereco() {
        Endereco endereco = Endereco.builder()
                    .logradouro("Rua ABC")
                    .numero("74")
                    .bairro("Centro")
                    .complemento("Próximo a praça")
                    .cidade("São Paulo")
                    .cep("02011-000")
                    .uf(TipoUF.SP)
                    .build();
        return endereco;
    }

}
