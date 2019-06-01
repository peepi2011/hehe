package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import DAI1819.OBD.Controller.ObdController;
import org.springframework.context.ApplicationContext;

public class GetControllers {

    private static ApplicationContext context = SpringContext.getApplicationContext();

    //Getter for the ObdController
    public ObdController getObdController() {
        ObdController obdController = (ObdController) context.getBean("ObdController");
        return obdController;
    }

    //Getter for the ObdAverageController
    public ObdAverageController getObdAverageController() {
        ObdAverageController obdAverageController = (ObdAverageController) context.getBean("ObdAverageController");
        return obdAverageController;
    }
}
