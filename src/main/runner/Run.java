package main.runner;

import main.map.RectangularMap;
import org.json.JSONObject;

public class Run {
    private int i = 0;
    public Run(RectangularMap map, JSONObject parameters) throws InterruptedException
    {
        long lastStepTIme = System.currentTimeMillis();
        while (true){
            if((boolean)parameters.get("isRunning")) {

                new Step(map);

                while (System.currentTimeMillis() - (long) (250.0 / (double) parameters.get("runSpeed")) < lastStepTIme) {
                    Thread.sleep(10);
                }

                lastStepTIme = System.currentTimeMillis();

                System.out.println(i);
                i++;
            }

            else
                Thread.sleep(50);

        }

    }

}
