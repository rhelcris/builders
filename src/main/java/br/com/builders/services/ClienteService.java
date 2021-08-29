package br.com.builders.services;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.api.response.ClienteResponse;

public interface ClienteService {

    ClienteResponse gravar(ClienteRequest clienteRequest);
    Object listarClientes(String numeroDocumento, String nomeCliente, Integer page, Integer pageSize);
    void excluir(Long codigo);
    ClienteResponse atualizar(Long id, ClienteRequest clienteRequest);
}
