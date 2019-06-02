package DAI1819.OBD;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
    
    @Scheduled(cron = "	59 23 * * Sun ")
    public void calculoPerfilRisco(){
        System.out.println("Ola");
        CalculoPerfilRisco calculo = new CalculoPerfilRisco();    
        calculo.calcularPerfisRisco();   
    }
    
}
