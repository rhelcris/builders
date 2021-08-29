package br.com.builders.utils;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.api.response.ClienteResponse;
import br.com.builders.model.Cliente;
import br.com.builders.model.Endereco;

import static java.util.Objects.nonNull;

public class ClienteUtils {

    public static Cliente transformar(ClienteRequest clienteRequest) {
        Endereco endereco = null;
        if(nonNull(clienteRequest.getEndereco())) {
            endereco = Endereco.builder()
                    .logradouro(clienteRequest.getEndereco().getLogradouro())
                    .numero(clienteRequest.getEndereco().getNumero())
                    .bairro(clienteRequest.getEndereco().getBairro())
                    .complemento(clienteRequest.getEndereco().getComplemento())
                    .cidade(clienteRequest.getEndereco().getCidade())
                    .cep(clienteRequest.getEndereco().getCep())
                    .uf(clienteRequest.getEndereco().getUf())
                    .build();
        }

        Cliente cliente = Cliente.builder()
                .id(clienteRequest.getIdCliente())
                .nome(clienteRequest.getNomeCliente())
                .cpfCnpj(clienteRequest.getNumeroDocumento())
                .dataNascimento(clienteRequest.getDataNascimento())
                .endereco(endereco)
                .build();

        return cliente;
    }

    public static ClienteResponse transformar(Cliente cliente) {
        ClienteResponse response = ClienteResponse.builder()
                .idCliente(cliente.getId())
                .nomeCliente(cliente.getNome())
                .numeroDocumento(cliente.getCpfCnpj())
                .dataNascimento(cliente.getDataNascimento())
                .idade(cliente.getIdade())
                .endereco(cliente.getEndereco())
                .build();

        return response;
    }

    public static Cliente atualizarDados(Cliente cliente, ClienteRequest clienteRequest) {
        return Cliente.builder()
                .id(cliente.getId())
                .nome(clienteRequest.getNomeCliente())
                .cpfCnpj(clienteRequest.getNumeroDocumento())
                .dataNascimento(clienteRequest.getDataNascimento())
                .endereco(clienteRequest.getEndereco())
                .build();
    }
}
