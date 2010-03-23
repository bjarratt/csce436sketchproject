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
    
    double jitter = -1;

    int numSegmentsToClamp = -1;

    //int numSegmentsToStrobe = -1;

    Random rand = new Random();

    Persona(double jitter, int numSegmentsToClamp){//, int numSegmentsToStrobe){
        this.jitter = jitter;
        this.numSegmentsToClamp = numSegmentsToClamp;
        //this.numSegmentsToStrobe = numSegmentsToStrobe;
    }

    Persona(){
    }

    public Stroke Morph(Stroke s){
        Stroke newStroke = s;

        s.calcAvgSlope();

        if (numSegmentsToClamp > 0){
            newStroke = clampToFixedSegments(newStroke);
        }

        if (jitter > 0){
            newStroke = applyJitter(newStroke);
        }
        /*if (numSegmentsToStrobe > 0){
            newStroke = rainbowStrokes(newStroke);
        }*/

        return newStroke;
    }

    public Stroke applyJitter(Stroke s){
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

    public Stroke clampToFixedSegments(Stroke s){
        if (s.dataPoints.size() < 3 || (s.dataPoints.size()-1) <= numSegmentsToClamp){
            return s;
        }
        
        Stroke newStroke = new Stroke();

        double startTime = s.dataPoints.get(0).time;
        double endTime = s.dataPoints.get(s.dataPoints.size()-1).time;
        double deltaT = endTime - startTime;

        double tIncr = (deltaT / (double)numSegmentsToClamp);
        double expectedT = tIncr + startTime;
        Point currentClosestPoint = s.dataPoints.get(0);
        newStroke.dataPoints.add(currentClosestPoint);

        for(int i = 1; i < s.dataPoints.size(); i++){
            if (Math.abs(s.dataPoints.get(i).time - expectedT) <= Math.abs(currentClosestPoint.time - expectedT)){
                currentClosestPoint = s.dataPoints.get(i);
            }
            else{
                newStroke.dataPoints.add(currentClosestPoint);
                expectedT += tIncr;
                currentClosestPoint = s.dataPoints.get(i);
            }
        }

        newStroke.dataPoints.add(s.dataPoints.get(s.dataPoints.size()-1));

        return newStroke;
    }
    /*public Stroke rainbowStrokes(Stroke s){
        if (s.dataPoints.size() < 3 || (s.dataPoints.size()-1) <= numSegmentsToStrobe){
            return s;
        }

        Stroke newStroke = new Stroke();

        double startTime = s.dataPoints.get(0).time;
        double endTime = s.dataPoints.get(s.dataPoints.size()-1).time;
        double deltaT = endTime - startTime;

        double tIncr = (deltaT / (double)numSegmentsToStrobe);
        double expectedT = tIncr + startTime;
        Point currentClosestPoint = s.dataPoints.get(0);
        newStroke.dataPoints.add(currentClosestPoint);

        for(int i = 1; i < s.dataPoints.size(); i++){
            if (Math.abs(s.dataPoints.get(i).time - expectedT) <= Math.abs(currentClosestPoint.time - expectedT)){
                currentClosestPoint = s.dataPoints.get(i);
            }
            else{
                newStroke.dataPoints.add(currentClosestPoint);
                expectedT += tIncr;
                currentClosestPoint = s.dataPoints.get(i);
            }
        }

        newStroke.dataPoints.add(s.dataPoints.get(s.dataPoints.size()-1));

        return newStroke;
    }*/

}
