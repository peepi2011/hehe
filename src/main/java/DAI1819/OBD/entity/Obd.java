package DAI1819.OBD.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Obd")
@Component("Obd")
@IdClass(ObdId.class)
    public class Obd implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Id
        private String dataobd ;
    
        @Id
        private String time;
    
        @Column(name = "id_obd")
        private String id_obd;
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

    public Obd(String dataobd, String time, String id_obd, int totalkm, int rotacoes, int tempoviagem, int velocidade) {
        this.dataobd = dataobd;
        this.time = time;
        this.id_obd = id_obd;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId_obd() {
        return id_obd;
    }

    public void setId_obd(String id_obd) {
        this.id_obd = id_obd;
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

    
    
