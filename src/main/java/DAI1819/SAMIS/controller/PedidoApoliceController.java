package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.PedidoApolice;
import DAI1819.SAMIS.repository.PedidoApoliceRepository;
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
@Component("PedidoApoliceController")
public class PedidoApoliceController {

    @Autowired
    EntityManager em;
    @Autowired
    PedidoApoliceRepository pedidoApoliceRepository;

    //Get
    @GetMapping("/api/pedidoapolices")
    public List<PedidoApolice> getAllPedidosApolices() {
        return pedidoApoliceRepository.findAll();
    }

    @GetMapping("/api/pedidoapolices/{numero_pedido}")
    public ResponseEntity<PedidoApolice> getPedidoApoliceByNumeroPedido(@PathVariable(value = "numero_pedido") int numeroPedido) {
        PedidoApolice pedidoapolice = em.find(PedidoApolice.class, numeroPedido);
        return ResponseEntity.ok().body(pedidoapolice);
    }

    //Create
    @PostMapping(path = "/api/pedidoapolices", consumes = "application/json", produces = "application/json")
    public PedidoApolice createPedidoApolice(@Valid @RequestBody PedidoApolice pedidoapolice) {
        return pedidoApoliceRepository.save(pedidoapolice);
    }

    //Delete
    @DeleteMapping("/api/pedidoapolices/{numero_pedido}")
    public Map<String, Boolean> deletePedidoApolice(@PathVariable(value = "numero_pedido") int numeropedido) {
        PedidoApolice pedidoapolice = em.find(PedidoApolice.class, numeropedido);
        pedidoApoliceRepository.delete(pedidoapolice);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
