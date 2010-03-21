/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Justin
 */
public class Vector {

    double x        = 0; //x component of the vector
    double y        = 0; //y component of the vector
    double length   = 0; //length of the vector

    /**
     * Constructor
     * @param a
     * The starting point.
     * @param b
     * The ending point.
     */
    Vector(Point a, Point b){
        x = b.x_coor - a.x_coor;
        y = b.y_coor - a.y_coor;

        length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    Vector(){

    }

    void normalize(){
       x = x/length;
       y = y/length;

       length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }





}
