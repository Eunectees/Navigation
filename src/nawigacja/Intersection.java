
package nawigacja;

import java.awt.geom.Point2D;


public class Intersection{
    
    //punkt bazowy
    Point2D IntersectionBase;
    
    //punkty połączone z punktem bazowym 
    Point2D Intersection1;
    Point2D Intersection2;
    Point2D Intersection3;
    Point2D Intersection4;
    Point2D Intersection5;
    Point2D Intersection6;
    Point2D Intersection7;
    
    //punkt znajdujący się najbliżej końca ścieżki
    Point2D NajKon;
    
    //odleglosc od poszczegolnych punktow do punktu koncowego
    Double hx1 = 0.0;
    Double hx2 = 0.0;
    Double hx3 = 0.0;
    Double hx4 = 0.0;
    Double hx5 = 0.0;
    Double hx6 = 0.0;
    Double hx7 = 0.0;
    
    //odleglosc od poszczegolnych punktow do punktu poczatkowego
    Double gx1 = 0.0;
    Double gx2 = 0.0;
    Double gx3 = 0.0;
    Double gx4 = 0.0;
    Double gx5 = 0.0;
    Double gx6 = 0.0;
    Double gx7 = 0.0;
    
    public Intersection(){
        
        IntersectionBase = null;
        Intersection1 = null;
        Intersection2 = null;
        Intersection3 = null;
        Intersection4 = null;
        Intersection5 = null;
        Intersection6 = null;
        Intersection7 = null;
        NajKon = null;
    }
    
    public void Update(Point2D End){
        
         //odleglosc od poszczegolnych punktow do punktu koncowego
        if (Intersection1 != null) hx1 = End.distance(Intersection1);
        if (Intersection2 != null)hx2 = End.distance(Intersection2);
        if (Intersection3 != null)hx3 = End.distance(Intersection3);
        if (Intersection4 != null)hx4 = End.distance(Intersection4);
        if (Intersection5 != null)hx5 = End.distance(Intersection5);
        if (Intersection6 != null)hx6 = End.distance(Intersection6);
        if (Intersection7 != null)hx7 = End.distance(Intersection7);
        
        //odleglosc od poszczegolnych punktow do punktu poczatkowego
        if (Intersection1 != null) gx1 = IntersectionBase.distance(Intersection1);
        if (Intersection2 != null)gx2 = IntersectionBase.distance(Intersection2);
        if (Intersection3 != null)gx3 = IntersectionBase.distance(Intersection3);
        if (Intersection4 != null)gx4 = IntersectionBase.distance(Intersection4);
        if (Intersection5 != null)gx5 = IntersectionBase.distance(Intersection5);
        if (Intersection6 != null)gx6 = IntersectionBase.distance(Intersection6);
        if (Intersection7 != null)gx7 = IntersectionBase.distance(Intersection7);
        
        Double pomf = hx1+gx1;
        NajKon = Intersection1;
        if (hx2+gx2 < pomf) {NajKon = Intersection2; pomf=hx2+gx2;}
        if (hx3+gx3 < pomf) {NajKon = Intersection3; pomf=hx3+gx3;}
        if (Intersection4 != null)if (hx4+gx4 < pomf) {NajKon = Intersection4; pomf=hx4+gx4;}
        if (Intersection5 != null)if (hx5+gx5 < pomf) {NajKon = Intersection5; pomf=hx5+gx5;}
        if (Intersection6 != null)if (hx6+gx6 < pomf) {NajKon = Intersection6; pomf=hx6+gx6;}
        if (Intersection7 != null)if (hx7+gx7 < pomf) {NajKon = Intersection7; pomf=hx7+gx7;}
        
    }
}