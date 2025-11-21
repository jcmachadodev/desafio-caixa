package br.com.caixa.caixaverso.repository;

import br.com.caixa.caixaverso.domain.entity.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findByIdOrNull(Long id) {
        return findById(id);
    }

}
