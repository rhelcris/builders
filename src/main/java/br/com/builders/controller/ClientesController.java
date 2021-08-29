package br.com.builders.controller;

import br.com.builders.api.requests.ClienteRequest;
import br.com.builders.api.response.ClienteResponse;
import br.com.builders.services.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("clientes")
public class ClientesController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listarClientes(
            @RequestParam(name = "NUMERO_DOCUMENTO", required = false) String numeroDocumento,
            @RequestParam(name = "NOME_CLIENTE", required = false) String nomeCliente,
            @RequestParam(name = "PAGE", required = false) Integer page,
            @RequestParam(name = "PAGE_SIZE", required = false) Integer pageSize ) {
        log.info("Iniciando a listagem dos clientes");
        Object clientes = clienteService.listarClientes(numeroDocumento, nomeCliente, page, pageSize);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> gravar(@RequestBody ClienteRequest clienteRequest) {
        log.info("Iniciando a gravação do cliente");
        try {
            ClienteResponse response = clienteService.gravar(clienteRequest);
            log.info("Cliente cadastrado com sucesso!");
            return ResponseEntity.status(CREATED).body(response);
        } catch (Exception e) {
            log.error("erro ao cadastrar o cliente", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable("codigo") Long codigo,
                                                     @RequestBody ClienteRequest clienteRequest ) {
        log.info("Solicitaçäo de atualizar de dados do cliente " + codigo);
        ClienteResponse response = clienteService.atualizar(codigo, clienteRequest);
        log.info("Cliente atualizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluir(@PathVariable("codigo") Long codigo) {
        log.info("Iniciando a exclusão do cliente com codigo: " + codigo);
        try {
            clienteService.excluir(codigo);
            log.info("Exclusão realizada com sucesso.");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
