/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAI1819.OBD.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Asus
 */
public class ObdId implements Serializable {
    
    private String dataobd;
    private String time;

    public ObdId() {
    }

    public ObdId(String dataobd, String time) {
        this.dataobd = dataobd;
        this.time = time;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.dataobd);
        hash = 37 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObdId other = (ObdId) obj;
        if (!Objects.equals(this.dataobd, other.dataobd)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }
    
    

    
}
