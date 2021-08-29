package br.com.builders.api.response;

import br.com.builders.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {

    @JsonProperty("id_cliente")
    private Long idCliente;
    @JsonProperty("nome_cliente")
    private String nomeCliente;
    @JsonProperty("numero_documento")
    private String numeroDocumento;
    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;

    private Endereco endereco;

    @JsonProperty("idade_cliente")
    private int idade;

}
