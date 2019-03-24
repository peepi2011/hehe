package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.Utilizador;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import DAI1819.SAMIS.repository.UtilizadorRepository;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RestController
@Component("UtilizadorController")
public class UtilizadorController {

    @Autowired
    EntityManager em;
    @Autowired
    UtilizadorRepository utilizadorRepository;

    //Get the data of all users from databse and send them to the client
    @GetMapping("/api/users")
    public List<Utilizador> getAllUsers() {
        return utilizadorRepository.findAll();
    }

    //Get the data of a user from the database by is Id
    @GetMapping("/api/users/{id}")
    public ResponseEntity<Utilizador> getUserById(@PathVariable(value = "id") String userId) {
        Utilizador user = em.find(Utilizador.class, userId);
        return ResponseEntity.ok().body(user);
    }

//Creates a new user in the database with the data from the client
    @PostMapping(path = "/api/users", consumes = "application/json", produces = "application/json")
    public Utilizador createUser(@Valid @RequestBody Utilizador user) {
        return utilizadorRepository.save(user);
    }

//Updates the data on the database of a user by a given id
    @PutMapping("/api/users/{id}")
    public ResponseEntity<Utilizador> updateUser(@PathVariable(value = "id") String userId, @Valid @RequestBody Utilizador userDetails) {

        Utilizador user = em.find(Utilizador.class, userId);;

        user.setNome(userDetails.getNome());
        user.setSexo(userDetails.getSexo());
        user.setEmail(userDetails.getEmail());
        user.setMorada(userDetails.getMorada());
        user.setDataNascimento(userDetails.getDataNascimento());
        user.setCodigoPostal(userDetails.getCodigoPostal());
        user.setTelemovel(userDetails.getTelemovel());
        user.setTipoUtilizador(userDetails.getTipoUtilizador());

        final Utilizador updatedUser = utilizadorRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    //Deletes a user from the database by a given id
    @DeleteMapping("/api/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") String userId) {

        Utilizador user = em.find(Utilizador.class, userId);
        utilizadorRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }
}
