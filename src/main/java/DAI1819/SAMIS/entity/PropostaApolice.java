
package DAI1819.SAMIS.entity;
import javax.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "proposta_apolice")
@Component("PropostaApolice")
public class PropostaApolice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numero_proposta;
    
    @Column(name = "descricao", nullable = false)
    private String descricao;
        
    @Column(name = "numero_pedido", nullable = false)
    private int numero_pedido;
    
    @Column(name = "id_ficheiro", nullable = false)
    private int id_ficheiro;
    

    public PropostaApolice(int numero_proposta, String descricao, int numero_pedido, int id_ficheiro) {
        this.numero_proposta = numero_proposta;
        this.descricao = descricao;
        this.numero_pedido = numero_pedido;
        this.id_ficheiro = id_ficheiro;
    }

    public PropostaApolice() {
    }

    public int getNumero_proposta() {
        return numero_proposta;
    }

    public void setNumero_proposta(int numero_proposta) {
        this.numero_proposta = numero_proposta;
    }
    
    public int getId_ficheiro() {
        return id_ficheiro;
    }

    public void setId_ficheiro(int id_ficheiro) {
        this.id_ficheiro = id_ficheiro;
    }
    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(int numero_pedido) {
        this.numero_pedido = numero_pedido;
    }
    
}
