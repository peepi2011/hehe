package DAI1819.SAMIS.repository;

import DAI1819.SAMIS.entity.PedidoApolice;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/pedidoapolices")
@Component("PedidoApoliceRepository")
public interface PedidoApoliceRepository extends JpaRepository<PedidoApolice, Integer> {
    
}
