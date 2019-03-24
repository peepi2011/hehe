
package DAI1819.SAMIS.entity;
import javax.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "pedido_apolice")
@Component("Pedido_Apolice")
public class PedidoApolice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numero_pedido;
    
    @Column(name = "nif", nullable = false)
    private int nif;
        
    @Column(name = "matricula", nullable = false)
    private String matricula;
    
    @Column(name = "tipo_seguro", nullable = false)
    private String tipo_seguro;
    
    @Column(name = "detalhes", nullable = false)
    private String detalhes;
    
    @Column(name = "data_pedido", nullable = false)
    private String data_pedido;

    public PedidoApolice() {
    }

    public PedidoApolice(int numero_pedido, int nif, String matricula, String tipo_seguro, String detalhes, String data_pedido) {
        this.numero_pedido = numero_pedido;
        this.nif = nif;
        this.matricula = matricula;
        this.tipo_seguro = tipo_seguro;
        this.detalhes = detalhes;
        this.data_pedido = data_pedido;
    }

    public int getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(int numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo_seguro() {
        return tipo_seguro;
    }

    public void setTipo_seguro(String tipo_seguro) {
        this.tipo_seguro = tipo_seguro;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(String data_pedido) {
        this.data_pedido = data_pedido;
    }

    
}
