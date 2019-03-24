package DAI1819.SAMIS.entity;

import DAI1819.SAMIS.EstadoApolice;
import javax.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "apolices")
@Component("Apolice")
public class Apolice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numero_apolice;

    @Column(name = "nif", nullable = false)
    private int nif;
    
    @Column(name = "perfil_risco", nullable = false)
    private int perfilRisco;
    
    @Column(name = "seguradora", nullable = false)
    private String seguradora;
    
    @Column(name = "matricula", nullable = false)
    private String matricula;
    
    @Column(name = "data_subscricao", nullable = false)
    private String dataSubscricao;
    
    @Column(name = "data_termino", nullable = false)
    private String dataTermino;
    
    @Column(name = "premio", nullable = false)
    private String premio;
   
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoApolice estado;

    public Apolice() {
    }

    public Apolice(int numero_apolice, int nif, int perfilRisco, String seguradora, String matricula, String dataSubscricao, String dataTermino, String premio, EstadoApolice estado) {
        this.numero_apolice = numero_apolice;
        this.nif = nif;
        this.perfilRisco = perfilRisco;
        this.seguradora = seguradora;
        this.matricula = matricula;
        this.dataSubscricao = dataSubscricao;
        this.dataTermino = dataTermino;
        this.premio = premio;
        this.estado = estado;
    }

    public int getNumero_apolice() {
        return numero_apolice;
    }

    public int getNif() {
        return nif;
    }

    public int getPerfilRisco() {
        return perfilRisco;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getDataSubscricao() {
        return dataSubscricao;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public String getPremio() {
        return premio;
    }

    public EstadoApolice getEstado() {
        return estado;
    }

    public void setNumero_apolice(int numero_apolice) {
        this.numero_apolice = numero_apolice;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void setPerfilRisco(int perfilRisco) {
        this.perfilRisco = perfilRisco;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setDataSubscricao(String dataSubscricao) {
        this.dataSubscricao = dataSubscricao;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public void setEstado(EstadoApolice estado) {
        this.estado = estado;
    }

   

}
