package DAI1819.OBD;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
    
    @Scheduled
    public void calculoPerfilRisco(){   
    CalculoPerfilRisco calculo = new CalculoPerfilRisco();    
    calculo.calcularPerfisRisco();   
    }
    
}
