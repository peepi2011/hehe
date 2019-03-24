package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.PropostaApolice;
import DAI1819.SAMIS.repository.PropostaApoliceRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RestController
@Component("PropostaApoliceController")
public class PropostaApoliceController {

    @Autowired
    EntityManager em;
    @Autowired
    PropostaApoliceRepository propostaApoliceRepository;

    //Get
    @GetMapping("/api/propostaapolices")
    public List<PropostaApolice> getAllPropostasApolices() {
        return propostaApoliceRepository.findAll();
    }

    @GetMapping("/api/propostaapolices/{numero_proposta}")
    public ResponseEntity<PropostaApolice> getPropostaApoliceByNumeroProposta(@PathVariable(value = "numero_proposta") int numeroProposta) {
        PropostaApolice propostaapolice = em.find(PropostaApolice.class, numeroProposta);
        return ResponseEntity.ok().body(propostaapolice);
    }

    //Create
    @PostMapping(path = "/api/propostaapolices", consumes = "application/json", produces = "application/json")
    public PropostaApolice createPropostaApolice(@Valid @RequestBody PropostaApolice propostaapolice) {
        return propostaApoliceRepository.save(propostaapolice);
    }

    //Delete
    @DeleteMapping("/api/propostaapolices/{numero_proposta}")
    public Map<String, Boolean> deletePropostaApolice(@PathVariable(value = "numero_proposta") int numeroProposta) {
        PropostaApolice propostaapolice = em.find(PropostaApolice.class, numeroProposta);
        propostaApoliceRepository.delete(propostaapolice);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
