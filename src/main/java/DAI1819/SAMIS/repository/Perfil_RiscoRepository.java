package DAI1819.SAMIS.repository;

import DAI1819.SAMIS.entity.Perfil_Risco;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/perfis_risco")
@Component("Perfis_RiscoRepository")
public interface Perfil_RiscoRepository extends JpaRepository<Perfil_Risco, Integer> {
    
}
