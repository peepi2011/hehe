package DAI1819.OBD.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "obd")
@Component("obd")
@IdClass(ObdId.class)
    public class Obd implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Id
        private String dataobd ;
    
        @Id
        private String hora;
    
        @Column(name = "idobd")
        private String idobd;
        @NotNull
        
        @Column(name = "totalkm")
        private int totalkm;
        
        @Column(name = "rotacoes")
        private int rotacoes;
        
        @Column(name = "tempoviagem")
        private int tempoviagem;
        
        @Column(name = "velocidade")
        private int velocidade;

    public Obd() {
    }

    public Obd(String dataobd, String time, String idobd, int totalkm, int rotacoes, int tempoviagem, int velocidade) {
        this.dataobd = dataobd;
        this.hora = time;
        this.idobd = idobd;
        this.totalkm = totalkm;
        this.rotacoes = rotacoes;
        this.tempoviagem = tempoviagem;
        this.velocidade = velocidade;
    }

    public String getDataobd() {
        return dataobd;
    }

    public void setDataobd(String dataobd) {
        this.dataobd = dataobd;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdobd() {
        return idobd;
    }

    public void setIdobd(String idobd) {
        this.idobd = idobd;
    }

    public int getTotalkm() {
        return totalkm;
    }

    public void setTotalkm(int totalkm) {
        this.totalkm = totalkm;
    }

    public int getRotacoes() {
        return rotacoes;
    }

    public void setRotacoes(int rotacoes) {
        this.rotacoes = rotacoes;
    }

    public int getTempoviagem() {
        return tempoviagem;
    }

    public void setTempoviagem(int tempoviagem) {
        this.tempoviagem = tempoviagem;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
       
}

    
    
