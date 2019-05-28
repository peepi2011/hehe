package DAI1819.OBD.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


import org.springframework.stereotype.Component;

@Entity
@Table(name = "Average")
@Component("Average")
    public class Obd implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Id
        @Column(name = "idObd")
        private String idObd;
        @NotNull
        
        @Column(name = "velocidade")
        private int velocidade;
        
        @Column(name = "rotacoes")
        private int rotacoes;
        
        @Column(name = "km")
        private int km;
        
        @Column(name = "tempo")
        private String tempo;

        
        public Obd() {
        }
        
        public Obd(String idObd, int velocidade, int rotacoes, int km, String tempo) {
            this.idObd = idObd;
            this.velocidade = velocidade;
            this.rotacoes = rotacoes;
            this.km = km;
            this.tempo = tempo;
        }

        public String getIdObd() {
            return idObd;
        }

        public void setIdObd(String idObd) {
            this.idObd = idObd;
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

    
    
