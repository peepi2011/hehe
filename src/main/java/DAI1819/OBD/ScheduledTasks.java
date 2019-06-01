package DAI1819.OBD;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
    
    @Scheduled(cron = "	0 0/1 * 1/1 * ? *")
    public void calculoPerfilRisco(){
        System.out.println("Ola");
    //CalculoPerfilRisco calculo = new CalculoPerfilRisco();    
    //calculo.calcularPerfisRisco();   
    }
    
}
