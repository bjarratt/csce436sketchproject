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

    Persona(){
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

    public Stroke subdivide(Stroke s){
        Stroke subdividedStroke = new Stroke();
        Stroke averagedStroke = new Stroke();
        double x;
        double y;
        double time;
        double pressure;

        //linear Subdivision
        for(int i = 0; i < s.dataPoints.size(); i++){
            subdividedStroke.dataPoints.add(s.dataPoints.get(i));

            if(i < s.dataPoints.size()-1){
                x = (s.dataPoints.get(i).x_coor + s.dataPoints.get(i+1).x_coor)/2;
                y = (s.dataPoints.get(i).y_coor + s.dataPoints.get(i+1).y_coor)/2;
                time = (s.dataPoints.get(i).time + s.dataPoints.get(i+1).time)/2;
                pressure = (s.dataPoints.get(i).pressure + s.dataPoints.get(i+1).pressure)/2;

                Point p = new Point(x, y, time, pressure);
                subdividedStroke.dataPoints.add(p);
            }
        }

        //averaging
        for(int i = 0; i < subdividedStroke.dataPoints.size(); i++){

            if(i < subdividedStroke.dataPoints.size()-1){
                x = (subdividedStroke.dataPoints.get(i).x_coor + subdividedStroke.dataPoints.get(i+1).x_coor)/2;
                y = (subdividedStroke.dataPoints.get(i).y_coor + subdividedStroke.dataPoints.get(i+1).y_coor)/2;
                time = (subdividedStroke.dataPoints.get(i).time + subdividedStroke.dataPoints.get(i+1).time)/2;
                pressure = (subdividedStroke.dataPoints.get(i).pressure + subdividedStroke.dataPoints.get(i+1).pressure)/2;

                Point p = new Point(x, y, time, pressure);
                averagedStroke.dataPoints.add(p);
            }
        }
        return averagedStroke;
    }

}
