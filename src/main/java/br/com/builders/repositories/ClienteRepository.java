package br.com.builders.repositories;

import br.com.builders.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContainingAndAndCpfCnpjContaining(String nome, String cpfCnpj);
    Page<Cliente> findByNomeContainingAndAndCpfCnpjContaining(String nome, String cpfCnpj, Pageable pageable);
    Cliente findOneById(Long id);

}
