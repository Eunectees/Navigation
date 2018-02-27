package nawigacja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class FrameMap extends JFrame{
    
    //zmienne pomocnicze, przechowywujące rozmiar okna
    int SizeFrameY;
    int SizeFrameX;
    
    //zmienna pomocnicza przechowywująca ilość węzłów
    int NumberOfNodes;
        
    public FrameMap(){
        
        //pobranie rozmiarow okna od uzytkownika
        SizeFrameX = Integer.parseInt(JOptionPane.showInputDialog("Width window: (recommended 600)"));
        SizeFrameY = Integer.parseInt(JOptionPane.showInputDialog("Height window: (recommended 600)"));
        
        //zmienna pomocnicza, obliczająca najodpowiedniejsza maxymalna liczbe wezlow
        int PomNumberOfNodes = (SizeFrameX * SizeFrameY) / 16090;
        
        //pobranie ilosci wezlow od uzytkownika
        NumberOfNodes = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of nodes: (Scope: 5-" + PomNumberOfNodes + ")"));
        
        setTitle("\"Navigation\"");
        setSize(SizeFrameX,SizeFrameY);
        setResizable(false);
        
        paintComponent comp = new paintComponent();
        comp.NumberOfNodes(NumberOfNodes);//przekazanie ilosci wezlow
        comp.SizeFrame(SizeFrameX, SizeFrameY);//przekazanie rozmiarow okna
        add(comp);
        
        //informacja, legenda
        JOptionPane.showMessageDialog(null,"Start: Red point \nEnd: Green point");
        
        JPanel ButtonPanel = new JPanel();
        JButton NewMapButton = new JButton("New maps");
            ButtonAction NewMapAction = new ButtonAction("NewMapButton", comp, this);
            NewMapButton.addActionListener(NewMapAction);
            ButtonPanel.add(NewMapButton, BorderLayout.SOUTH);
          
            
            add(ButtonPanel, BorderLayout.SOUTH);
    }

}

class paintComponent extends JComponent{
    
        //zmienne przechowywujace rozmiar okna
        int SizeFrameX;
        int SizeFrameY;
        
        //rozmiar tablicy
        int TabSize = 15;
    
        //obramowanie mapy
         Rectangle2D Obramowanie;
         
         //tablica przechowywująca obiekty
         Object[] Points;
          
         //Kolorystyczne oznaczenie skrzyzowań
         Ellipse2D EllipseStart = new Ellipse2D.Double(0, 0, 0, 0);
         Ellipse2D Ellipse = new Ellipse2D.Double(0, 0, 0, 0);
         Ellipse2D EllipseEnd = new Ellipse2D.Double(0, 0, 0, 0);
         
         Object[] Ellipses = {EllipseStart,Ellipse,EllipseEnd};
         
         
         //tablica przechowywujaca odleglosci miedzy punktami
         Object[] Distanse;
         
         //tablica przechoywujaca skrzyzowania, pierowsza pozycja to punkt poczatkowy, ostatnia to punkt koncowy
         Object[] Intersections;
         
         Graphics g = null;
         Graphics2D g2d = (Graphics2D) g;
         
    //metoda pobierajca rozmiar okna z głownej fukcji oraz ustawiajaca obramowanie mapy
    public void SizeFrame(int x, int y) {
    
        SizeFrameX = x;
        SizeFrameY = y;
        
        //obramowanie mapy
        Obramowanie = new Rectangle2D.Double(10,10,SizeFrameX-30,SizeFrameY-95);        
    }
    
    
    //metoda pobierajaca ilosc wezlow
    public void NumberOfNodes(int x){
        TabSize = x;
    }
     
    public void paintComponent(Graphics g) {
        
        
        //ustalenie rozmiaru tablic
        Points = new Object[TabSize]; 
        Intersections = new Object[TabSize]; 
        Distanse = new Object[TabSize];
        
        //zaplenienie tablic
        for(int i=0; i<TabSize; i++) {
            Points[i] = new Point2D.Double(1000, 1000);
            Intersections[i] = new Intersection();
        }
        
        
         super.paintComponent(g);
	 g2d = (Graphics2D) g;

         
                g2d.draw(Obramowanie);
                
                                 
                Random generator = new Random();
                for(int i=0; i<TabSize; i++) {
                    
                    //ustawienie koloru punktu poczatkowego oraz koncowego
                    if (i == 0) g2d.setPaint(Color.RED);
                    if (i == (TabSize-1)) g2d.setPaint(Color.GREEN);
                    
                    //obszar w jakim mają być losowane punkty
                    int a = generator.nextInt(SizeFrameX-50)+20;
                    int b = generator.nextInt(SizeFrameY-115)+20;
                    
                    //ustawienie punktu poczatkowego w lewym gornym rogu
                    if (i == 0){
                        a = generator.nextInt(85)+20;
                        b = generator.nextInt(85)+20;
                    }
                    
                    Point2D PointPom = new Point2D.Double(a, b);
                    //sprawdzam czy tworzony punkt nie lezy blizej niz 100, od najblizszego, jesli lezy to losuje nowa pozycje
                    for (int l=0;l<15;l++)
                    for (int k=0;k<i;k++){
                        Point2D PointPomDist = (Point2D)Points[k];
                        float DistansePom = (float)PointPom.distance(PointPomDist);
                        
                        if (DistansePom < 100.0){
                            a = generator.nextInt(SizeFrameX-50)+20;
                            b = generator.nextInt(SizeFrameY-115)+20;
                            
                            PointPom = new Point2D.Double(a, b);
                            k = 0; 
                        }
                    }
                    
                    //dodanie wylosowanego punktu do tablicy punktow
                    Points[i] = PointPom;
                    
                    //rysowanie skrzyzowan
                    Ellipse2D EllipsePom = new Ellipse2D.Double(a-5, b-5, 8, 8);
                    if (i == 0) Ellipses[0] = EllipsePom;
                    if (i == TabSize-1) Ellipses[2] = EllipsePom;
                    if ((i != (TabSize-1))&&(i != 0)) Ellipses[1] = EllipsePom;
                    
                   
                    g2d.draw(EllipsePom);               
                    g2d.fill(EllipsePom);   
                    g2d.setPaint(Color.BLUE);
                 }
                
                //wyznaczanie odleglosci pomiedzy punktami
                for(int i=0;i<TabSize;i++){
                    Point2D PointPom = (Point2D)Points[i];
                    
                    for(int k=0;k<TabSize;k++){
                        Point2D PointPomDist = (Point2D)Points[k];
                        Distanse[k] = PointPom.distance(PointPomDist);
                    }
                    
                    //posortowanie tablicy przechowywujacej odleglosci do skrzyzowan
                    Sortowanie_b(Distanse, TabSize);
                
                    //wpisanie skrzyzowania bazowego oraz najblizszych trzech skrzyzowan ktore z nim sie krzyzuja 
                    Intersection IntersectionPom = new Intersection();
                    IntersectionPom.IntersectionBase = PointPom;
                    for(int k=0;k<TabSize;k++){
                        Point2D PointPomDist = (Point2D)Points[k];
                        Double DPom = PointPom.distance(PointPomDist);
                        if (DPom.equals(Distanse[1])){
                            IntersectionPom.Intersection1 = (Point2D)Points[k];
                        }
                        if (DPom.equals(Distanse[2])){
                            IntersectionPom.Intersection2 = (Point2D)Points[k];
                        }
                        if (DPom.equals(Distanse[3])){
                            IntersectionPom.Intersection3 = (Point2D)Points[k];
                        }
                    }
                    
                    Intersections[i] = IntersectionPom;
                    
                    //rysowanie dróg 
                    Line2D w = new Line2D.Double(IntersectionPom.IntersectionBase, IntersectionPom.Intersection1);
                    g2d.draw(w);
                    w = new Line2D.Double(IntersectionPom.IntersectionBase, IntersectionPom.Intersection2);
                    g2d.draw(w);
                    w = new Line2D.Double(IntersectionPom.IntersectionBase, IntersectionPom.Intersection3);
                    g2d.draw(w);
                
                } 
                
                //sprawdzenie czy do skrzyzowania dochodza inne drogi poza ustalonymi trzema poczatkowymi
                for(int i=0;i<TabSize;i++){
                    Intersection IntersectionPom = (Intersection)Intersections[i];
                    
                    for(int k=0;k<TabSize;k++){
                        Intersection IntersectionPom1 = (Intersection)Intersections[k];
                        
                        if(IntersectionPom.Intersection1.equals(IntersectionPom1.IntersectionBase) ){
                            if (IntersectionPom1.Intersection1.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection2.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection3.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection4 == null){IntersectionPom1.Intersection4 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection4.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection5 == null){IntersectionPom1.Intersection5 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection5.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection6 == null){IntersectionPom1.Intersection6 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection6.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection7 == null){IntersectionPom1.Intersection7 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection7.equals(IntersectionPom.IntersectionBase)){}
                        }
                        if(IntersectionPom.Intersection2.equals(IntersectionPom1.IntersectionBase) ){
                            if (IntersectionPom1.Intersection1.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection2.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection3.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection4 == null){IntersectionPom1.Intersection4 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection4.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection5 == null){IntersectionPom1.Intersection5 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection5.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection6 == null){IntersectionPom1.Intersection6 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection6.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection7 == null){IntersectionPom1.Intersection7 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection7.equals(IntersectionPom.IntersectionBase)){}
                        }
                        if(IntersectionPom.Intersection3.equals(IntersectionPom1.IntersectionBase) ){
                            if (IntersectionPom1.Intersection1.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection2.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection3.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection4 == null){IntersectionPom1.Intersection4 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection4.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection5 == null){IntersectionPom1.Intersection5 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection5.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection6 == null){IntersectionPom1.Intersection6 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection6.equals(IntersectionPom.IntersectionBase)){}
                            else if (IntersectionPom1.Intersection7 == null){IntersectionPom1.Intersection7 = IntersectionPom.IntersectionBase;}
                            else if (IntersectionPom1.Intersection7.equals(IntersectionPom.IntersectionBase)){}
                        }
                    }
                }
                
              
                //szukanie drogi od punktu A do punktu B - zmienne                
                Intersection IntersectionS = (Intersection)Intersections[0]; Point2D PointS = IntersectionS.IntersectionBase;
                Intersection IntersectionE = (Intersection)Intersections[TabSize-1]; Point2D PointE = IntersectionE.IntersectionBase;
                
                for(int i=0;i<TabSize;i++){
                Intersection IntersectionPom = (Intersection)Intersections[i];
                IntersectionPom.Update(IntersectionE.IntersectionBase);
                Intersections[i] = IntersectionPom;
                }
                
                Intersection IntersectionPom = (Intersection)Intersections[0];
                Intersection IntersectionNajwiekszy = (Intersection)Intersections[0];
                Point2D AktualPoint = null;
                
                List Droga = new ArrayList(); //lista przechowywujaca droge
                Droga.add(IntersectionNajwiekszy.IntersectionBase);//dodanie punktu startu
                
                //wyznaczanie drogi  od punktu A do punktu B   
                while(AktualPoint != PointE){
                        for(int i=0;i<TabSize;i++){
                        Intersection IntersectionPomZnajdz = (Intersection)Intersections[i];
                        if (IntersectionPomZnajdz.IntersectionBase.equals(IntersectionPom.NajKon))
                        {IntersectionNajwiekszy = IntersectionPomZnajdz;}
                        }
                    
                    Droga.add(IntersectionNajwiekszy.IntersectionBase);
                    
                    IntersectionPom = IntersectionNajwiekszy;
          
                    AktualPoint= IntersectionNajwiekszy.IntersectionBase;                     
                }
                
                long start = System.currentTimeMillis();//czas od poczatku programu
                long stop;//czas od poczatku programu
                
                //rysowanie drogi
                while(Droga != null){
                    if (Droga.size()==1) break;
                    
                    Point2D IntersectionPom1 = (Point2D)Droga.get(0);
                    Point2D IntersectionPom2 = (Point2D)Droga.get(1);
                    
                    g2d.setPaint(Color.RED);
                    Line2D linia = new Line2D.Double(IntersectionPom1, IntersectionPom2);
            
                    g2d.draw(linia);
                    
                    Droga.remove(0);
                    stop = System.currentTimeMillis();//czas od poczatku programu
                    
                    if(stop == 3000) break;//jesli petla wykonuje sie dluzej niz 3 sekundy to przerwij 
                }
               
                
                
                
}
    
    
        //metoda sortująca odleglości do poszczególnych skrzyżowań
        private static void Sortowanie_b(Object tablica[], int Size) {

        int i,zmiana;
        Double temp;
        
        do {
        zmiana=0;
        i=Size-1;
        do {
        i--;
        if (((Double)tablica[i+1]) < ((Double)tablica[i])) {
        temp=(Double)tablica[i];            
        tablica[i]=tablica[i+1];
        tablica[i+1]=temp;
        
        
        zmiana=1;
        }
        } while (i!=0);
        } while (zmiana!=0);

        }


}


class ButtonAction implements ActionListener{

            FrameMap frame;
            paintComponent comp;
            String nazwa;
            public ButtonAction(String s, paintComponent comp1, FrameMap frame1){
                nazwa = s;
                comp = comp1;
                frame = frame1;
            }

            public void actionPerformed(ActionEvent e) {

                if (nazwa == "NewMapButton"){
                comp = new paintComponent();
                comp.revalidate();
                comp.repaint();
                frame.revalidate();
                frame.repaint();
                    //informacja, legenda
                    JOptionPane.showMessageDialog(null,"Start: Red point \nEnd: Green point");
        
                }
            }
            
            }