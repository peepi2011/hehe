package DAI1819.OBD.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


import org.springframework.stereotype.Component;

@Entity
@Table(name = "Average")
@Component("Average")
    public class ObdAverage implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Id
        @Column(name = "id_obd")
        private String id_obd;
        @NotNull
        
        @Column(name = "tempo")
        private String tempo;
        
        @Column(name = "velocidade")
        private int velocidade;
        
        @Column(name = "rotacoes")
        private int rotacoes;
        
        @Column(name = "km")
        private int km;
        
        
        public ObdAverage() {
        }
        
        public ObdAverage(String idObd, int velocidade, int rotacoes, int km, String tempo) {
            this.id_obd = idObd;
            this.velocidade = velocidade;
            this.rotacoes = rotacoes;
            this.km = km;
            this.tempo = tempo;
        }

        public String getIdObd() {
            return id_obd;
        }

        public void setIdObd(String idObd) {
            this.id_obd = idObd;
        }

        public int getVelocidade() {
            return velocidade;
        }

        public void setVelocidade(int velocidade) {
            this.velocidade = velocidade;
        }

        public int getRotacoes() {
            return rotacoes;
        }

        public void setRotacoes(int rotacoes) {
            this.rotacoes = rotacoes;
        }

        public int getKm() {
            return km;
        }

        public void setKm(int km) {
            this.km = km;
        }

        public String getTempo() {
            return tempo;
        }

        public void setTempo(String tempo) {
            this.tempo = tempo;
        }
    }

    
    
