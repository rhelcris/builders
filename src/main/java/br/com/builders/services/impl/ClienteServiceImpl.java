package br.com.builders.services.impl;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.api.response.ClienteResponse;
import br.com.builders.exception.BusinessException;
import br.com.builders.model.Cliente;
import br.com.builders.repositories.ClienteRepository;
import br.com.builders.services.ClienteService;
import br.com.builders.utils.ClienteUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Service
@Slf4j
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Override
    public Object listarClientes(String numeroDocumento, String nomeCliente, Integer page, Integer pageSize) {
        numeroDocumento = nonNull(numeroDocumento) ? numeroDocumento : "";
        nomeCliente = nonNull(nomeCliente) ? nomeCliente : "";

        if(nonNull(page) && nonNull(pageSize)) {
            Pageable pageAndSize = PageRequest.of(page, pageSize);
            Page<Cliente> paginaDeClientes = clienteRepository.findByNomeContainingAndAndCpfCnpjContaining(nomeCliente, numeroDocumento, pageAndSize);
            return new PageImpl<>(getTransformedContent(paginaDeClientes), paginaDeClientes.getPageable(), paginaDeClientes.getTotalElements() );
        } else {
            List<Cliente> clientes = clienteRepository.findByNomeContainingAndAndCpfCnpjContaining(nomeCliente, numeroDocumento);
            return getTransformedContent(clientes);
        }
    }

    @Override
    @Transactional
    public void excluir(Long codigo) {
        Cliente cliente = clienteRepository.findOneById(codigo);
        if(nonNull(cliente)){
            clienteRepository.delete(cliente);
        } else {
            throw new BusinessException("Cliente nao existe");
        }
    }

    @Override
    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest clienteRequest) {
        Cliente cliente = clienteRepository.findOneById(id);
        Cliente clienteParaAtualizar = ClienteUtils.atualizarDados(cliente, clienteRequest);
        Cliente updatedCliente = clienteRepository.saveAndFlush(clienteParaAtualizar);
        return ClienteUtils.transformar(updatedCliente);
    }


    @Override
    @Transactional
    public ClienteResponse gravar(ClienteRequest clienteRequest) {
        if(validar(clienteRequest)) {
            Cliente cliente = ClienteUtils.transformar(clienteRequest);
            Cliente clienteSalvo = clienteRepository.save(cliente);
            return ClienteUtils.transformar(clienteSalvo);
        }
        throw new BusinessException("Erro ao cadastrar cliente");
    }


    private boolean validar(ClienteRequest clienteRequest) {
        if(!hasText(clienteRequest.getNomeCliente())) {
            log.error("Nome é obrigatório");
            return false;
        }
        if(!hasText(clienteRequest.getNumeroDocumento())) {
            log.error("Número do documento é obrigatório");
            return false;
        }
        return true;
    }

    private List<ClienteResponse> getTransformedContent(Page<Cliente> paginaDeClientes) {
        return paginaDeClientes.getContent().stream()
                .map(ClienteUtils::transformar)
                .collect(Collectors.toList());
    }

    private List<ClienteResponse> getTransformedContent(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteUtils::transformar)
                .collect(Collectors.toList());
    }

}
