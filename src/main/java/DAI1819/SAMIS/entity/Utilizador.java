package DAI1819.SAMIS.entity;

import DAI1819.SAMIS.TipoUtilizador;
import java.io.Serializable;
import javax.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "utilizadores")
@Component("Utilizador")
public class Utilizador implements Serializable {

    @Id
    private String nif;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "morada", nullable = false)
    private String morada;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @Column(name = "telemovel", nullable = false)
    private String telemovel;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "tipo_utilizador", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUtilizador tipoUtilizador;

    public Utilizador() {
    }

    public Utilizador(String nif, String nome, String sexo, String email, String morada, String dataNascimento, String codigoPostal, String telemovel, String password, TipoUtilizador tipoUtilizador) {
        this.nif = nif;
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.morada = morada;
        this.dataNascimento = dataNascimento;
        this.codigoPostal = codigoPostal;
        this.telemovel = telemovel;
        this.password = password;
        this.tipoUtilizador = tipoUtilizador;
    }

    public String getNIF() {
        return nif;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEmail() {
        return email;
    }

    public String getMorada() {
        return morada;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public String getPassword() {
        return password;
    }

    public TipoUtilizador getTipoUtilizador() {
        return tipoUtilizador;
    }

    public void setNIF(String nif) {
        this.nif = nif;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipoUtilizador(TipoUtilizador tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

}
