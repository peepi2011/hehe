package DAI1819.SAMIS.entity;

import javax.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "perfis_risco")
@Component("Perfis_Risco")
public class Perfil_Risco {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int perfil_risco;

    @Column(name = "velocidade_media", nullable = false)
    private String velocidade_media;
    
    @Column(name = "distração", nullable = false)
    private String distracao;
    
    @Column(name = "conducao_defensiva", nullable = false)
    private String conducao_defensiva;
    
    @Column(name = "agressividade", nullable = false)
    private String agressividade;
    
    @Column(name = "fadiga", nullable = false)
    private String fadiga;

    public Perfil_Risco() {
    }

    public Perfil_Risco(int perfil_risco, String velocidade_media, String distracao, String conducao_defensiva, String agressividade, String fadiga) {
        this.perfil_risco = perfil_risco;
        this.velocidade_media = velocidade_media;
        this.distracao = distracao;
        this.conducao_defensiva = conducao_defensiva;
        this.agressividade = agressividade;
        this.fadiga = fadiga;
    }

    public int getPerfil_risco() {
        return perfil_risco;
    }

    public void setPerfil_risco(int perfil_risco) {
        this.perfil_risco = perfil_risco;
    }

    public String getVelocidade_media() {
        return velocidade_media;
    }

    public void setVelocidade_media(String velocidade_media) {
        this.velocidade_media = velocidade_media;
    }

    public String getDistracao() {
        return distracao;
    }

    public void setDistracao(String distracao) {
        this.distracao = distracao;
    }

    public String getConducao_defensiva() {
        return conducao_defensiva;
    }

    public void setConducao_defensiva(String conducao_defensiva) {
        this.conducao_defensiva = conducao_defensiva;
    }

    public String getAgressividade() {
        return agressividade;
    }

    public void setAgressividade(String agressividade) {
        this.agressividade = agressividade;
    }

    public String getFadiga() {
        return fadiga;
    }

    public void setFadiga(String fadiga) {
        this.fadiga = fadiga;
    }

    
    

    
   

}
