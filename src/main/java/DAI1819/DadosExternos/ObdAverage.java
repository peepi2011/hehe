package DAI1819.DadosExternos;

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
        private String id_obd;
        
        @Column(name = "tempo")
        private double tempo;
        
        @Column(name = "velocidade")
        private int velocidade;
        
        @Column(name = "rotacoes")
        private int rotacoes;
        
        @Column(name = "km")
        private int km;
        
        
        public ObdAverage() {
        }
        
        public ObdAverage(String idObd, int velocidade, int rotacoes, int km, double tempo) {
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

        public double getTempo() {
            return tempo;
        }

        public void setTempo(double tempo) {
            this.tempo = tempo;
        }
    }

    
    
