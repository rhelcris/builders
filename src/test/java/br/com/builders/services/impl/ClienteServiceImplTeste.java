package br.com.builders.services.impl;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.api.response.ClienteResponse;
import br.com.builders.exception.BusinessException;
import br.com.builders.model.Cliente;
import br.com.builders.repositories.ClienteRepository;
import br.com.builders.services.ClienteService;
import br.com.builders.utils.ClienteUtils;
import br.com.builders.utils.ClienteUtilsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


public class ClienteServiceImplTeste {

    private final ClienteRepository clienteRepository = mock(ClienteRepository.class);

    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        this.clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    @DisplayName("Deve listar os clientes")
    public void deveListarOsClientes() {
        Cliente cliente = ClienteUtilsTest.umCliente();
        List<Cliente> listaRetornada =Arrays.asList(cliente);

        when(clienteRepository.findByNomeContainingAndAndCpfCnpjContaining(anyString(), anyString()))
                .thenReturn(listaRetornada);
        List<ClienteResponse> responseObtido = (List<ClienteResponse>) clienteService.listarClientes(anyString(), anyString(), null, null);

        Assertions.assertEquals(1, responseObtido.size());
    }

    @Test
    @DisplayName("Deve listar os clientes com paginacao")
    public void deveListarOsClientesComPaginacao() {
        Cliente cliente = ClienteUtilsTest.umCliente();
        String nomeCliente = "Fulano de Tal";
        String numeroDocumento = "50611634007";
        Integer pagina = 0;
        Integer pageSize = 10;
        List<Cliente> listaRetornada = Arrays.asList(cliente);

        PageRequest paginacao = PageRequest.of(pagina, pageSize);
        Page<Cliente> paginaCliente = new PageImpl<>(listaRetornada, paginacao, 1 );

        when(clienteRepository.findByNomeContainingAndAndCpfCnpjContaining(nomeCliente, numeroDocumento, paginacao))
                .thenReturn(paginaCliente);
        Page<ClienteResponse> responseObtido = (Page<ClienteResponse>) clienteService.listarClientes(numeroDocumento, nomeCliente, pagina, pageSize);

        Assertions.assertEquals(1, responseObtido.getContent().size());
    }

    @Test
    @DisplayName("Deve listar os clientes paginado")
    public void deveListarOsClientesPaginando() {

    }

    @Test
    @DisplayName("Deve cadastrar um cliente")
    public void deveCadastrarUmCliente() {
        ClienteRequest clienteRequest = ClienteUtilsTest.umClienteRequest();
        Cliente cliente = ClienteUtils.transformar(clienteRequest);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        ClienteResponse response = clienteService.gravar(clienteRequest);

        Assertions.assertEquals(1l, response.getIdCliente());
        Assertions.assertEquals(clienteRequest.getNomeCliente(), response.getNomeCliente());
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar um cliente sem Nome")
    public void deveFalharAoCadastrarUmClienteSemNome() {
        ClienteRequest clienteRequest = ClienteUtilsTest.umClienteRequest();
        clienteRequest.setNomeCliente("");

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class, () -> clienteService.gravar(clienteRequest)
        );

        Assertions.assertEquals("Erro ao cadastrar cliente", exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar um cliente sem Documento")
    public void deveFalharAoCadastrarUmClienteSemDocumento() {
        ClienteRequest clienteRequest = ClienteUtilsTest.umClienteRequest();
        clienteRequest.setNumeroDocumento("");

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class, () -> clienteService.gravar(clienteRequest)
        );

        Assertions.assertEquals("Erro ao cadastrar cliente", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um cliente")
    public void deveAtualizarUmCliente() {
        ClienteRequest clienteRequest = ClienteUtilsTest.umClienteRequest();
        Long idCliente = 1l;
        Cliente clienteOrigem = ClienteUtils.transformar(clienteRequest);

        clienteRequest.setNomeCliente("Matheus Brito");
        clienteRequest.setNumeroDocumento("50611634007");

        ClienteResponse clienteResponse = ClienteUtils.transformar(clienteOrigem);

        Cliente clienteAtualizado = ClienteUtils.atualizarDados(clienteOrigem, clienteRequest);

        when(clienteRepository.findOneById(anyLong())).thenReturn(clienteOrigem);
        when(clienteRepository.saveAndFlush(any(Cliente.class))).thenReturn(clienteAtualizado);
        ClienteResponse response = clienteService.atualizar(idCliente, clienteRequest);

        Assertions.assertEquals(1l, response.getIdCliente());
        Assertions.assertEquals(clienteRequest.getNomeCliente(), response.getNomeCliente());
        Assertions.assertEquals(clienteRequest.getNumeroDocumento(), response.getNumeroDocumento());
    }

    @Test
    @DisplayName("Deve excluir o cliente com sucesso")
    public void deveExcluirClienteComSucesso() {
        Long idCliente = 1l;
        Cliente cliente = ClienteUtilsTest.umCliente();

        when(clienteRepository.findOneById(anyLong())).thenReturn(cliente);
        doNothing().when(clienteRepository).delete(cliente);

        clienteService.excluir(idCliente);
    }

    @Test
    @DisplayName("Deve falhar ao excluir o cliente pois o cliente nao foi encontrado")
    public void deveFalharAoExcluirClienteQueNaoExiste() {
        Long idCliente = 1l;

        when(clienteRepository.findOneById(anyLong())).thenReturn(null);
        BusinessException exception = Assertions.assertThrows(
                BusinessException.class, () -> clienteService.excluir(idCliente)
        );

        Assertions.assertEquals("Cliente nao existe", exception.getMessage());
    }

}
