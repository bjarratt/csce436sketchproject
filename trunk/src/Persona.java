import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Justin
 */
public class Persona {
    
    double jitter = 0;
    Random rand = new Random();

    Persona(double jitter){
        this.jitter=jitter;
    }

    public Stroke Morph(Stroke s){
        Stroke newStroke = new Stroke();

        for(int i = 0; i < s.dataPoints.size(); i++){
            Point p = new Point();
            p.time = s.dataPoints.get(i).time;
            p.pressure = s.dataPoints.get(i).pressure;

            boolean add_value = rand.nextBoolean();
            double change = rand.nextDouble();

            if (add_value){
                p.x_coor = s.dataPoints.get(i).x_coor + (change*jitter);
            }
            else{
                p.x_coor = s.dataPoints.get(i).x_coor - (change*jitter);
            }

            add_value = rand.nextBoolean();

            if (add_value){
                p.y_coor = s.dataPoints.get(i).y_coor + (change*jitter);
            }
            else{
                p.y_coor = s.dataPoints.get(i).y_coor - (change*jitter);
            }

            newStroke.dataPoints.add(p);
        }

        return newStroke;
    }

}
