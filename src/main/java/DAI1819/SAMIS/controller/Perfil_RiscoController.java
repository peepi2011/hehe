package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.Perfil_Risco;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import DAI1819.SAMIS.repository.Perfil_RiscoRepository;

@RestController
@Component("Perfil_RiscoController")
public class Perfil_RiscoController {

    @Autowired
    EntityManager em;
    @Autowired
    Perfil_RiscoRepository perfis_riscoRepository;

    //Get
    @GetMapping("/api/perfis_risco")
    public List<Perfil_Risco> getAllPerfis_Risco() {
        return perfis_riscoRepository.findAll();
    }

    @GetMapping("/api/perfis_risco/{perfil_risco}")
    public ResponseEntity<Perfil_Risco> getPerfil_RiscoByPerfil_Risco(@PathVariable(value = "perfil_risco") int perfil_risco) {
        Perfil_Risco perfil = em.find(Perfil_Risco.class, perfil_risco);
        return ResponseEntity.ok().body(perfil);
    }

    //Create
    @PostMapping(path = "/api/perfis_risco", consumes = "application/json", produces = "application/json")
    public Perfil_Risco createPerfil_Risco(@Valid @RequestBody Perfil_Risco perfil) {
        return perfis_riscoRepository.save(perfil);
    }

    //Updates 
    @PutMapping("/api/perfis_risco/{perfil_risco}")
    public ResponseEntity<Perfil_Risco> updatePerfil_Risco(@PathVariable(value = "perfil_risco") int perfil_risco, @Valid @RequestBody Perfil_Risco perfil_riscoDetails) {

        Perfil_Risco perfil = em.find(Perfil_Risco.class, perfil_risco);

        
        perfil.setAgressividade(perfil_riscoDetails.getAgressividade());
        perfil.setConducao_defensiva(perfil_riscoDetails.getConducao_defensiva());
        perfil.setDistracao(perfil_riscoDetails.getDistracao());
        perfil.setFadiga(perfil_riscoDetails.getFadiga());
        perfil.setVelocidade_media(perfil_riscoDetails.getVelocidade_media());

        
        final Perfil_Risco updatedPerfil_Risco = perfis_riscoRepository.save(perfil);
        return ResponseEntity.ok(updatedPerfil_Risco);
    }

    //Delete
    @DeleteMapping("/api/perfis_risco/{perfil_risco}")
    public Map<String, Boolean> deletePerfis_Risco(@PathVariable(value = "perfil_risco") int perfil_risco) {
        Perfil_Risco perfil = em.find(Perfil_Risco.class, perfil_risco);
        perfis_riscoRepository.delete(perfil);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
