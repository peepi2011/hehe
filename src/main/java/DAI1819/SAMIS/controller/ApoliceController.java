package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.Apolice;
import DAI1819.SAMIS.repository.ApoliceRepository;
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
@Component("ApoliceController")
public class ApoliceController {

    @Autowired
    EntityManager em;
    @Autowired
    ApoliceRepository apoliceRepository;

    //Get
    @GetMapping("/api/apolices")
    public List<Apolice> getAllApolices() {
        return apoliceRepository.findAll();
    }

    @GetMapping("/api/apolices/{numero_apolice}")
    public ResponseEntity<Apolice> getApoliceByNumeroApolice(@PathVariable(value = "numero_apolice") int numeroApolice) {
        Apolice apolice = em.find(Apolice.class, numeroApolice);
        return ResponseEntity.ok().body(apolice);
    }

    //Create
    @PostMapping(path = "/api/apolices", consumes = "application/json", produces = "application/json")
    public Apolice createApolice(@Valid @RequestBody Apolice apolice) {
        return apoliceRepository.save(apolice);
    }

    //Updates 
    @PutMapping("/api/apolices/{numero_apolice}")
    public ResponseEntity<Apolice> updateApolice(@PathVariable(value = "numero_apolice") int numeroApolice, @Valid @RequestBody Apolice apoliceDetails) {

        Apolice apolice = em.find(Apolice.class, numeroApolice);

        apolice.setPerfilRisco(apoliceDetails.getPerfilRisco());
        apolice.setSeguradora(apoliceDetails.getSeguradora());
        apolice.setMatricula(apoliceDetails.getMatricula());
        apolice.setDataSubscricao(apoliceDetails.getDataSubscricao());
        apolice.setDataTermino(apoliceDetails.getDataTermino());
        apolice.setPremio(apoliceDetails.getPremio());
        apolice.setEstado(apoliceDetails.getEstado());

        final Apolice updatedApolice = apoliceRepository.save(apolice);
        return ResponseEntity.ok(updatedApolice);
    }

    //Delete
    @DeleteMapping("/api/apolices/{numero_apolice}")
    public Map<String, Boolean> deleteApolice(@PathVariable(value = "numero_apolice") int numeroapolice) {
        Apolice apolice = em.find(Apolice.class, numeroapolice);
        apoliceRepository.delete(apolice);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
