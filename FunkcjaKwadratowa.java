import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;

public class FunkcjaKwadratowa{
  public static void main(String[] args){
	  EventQueue.invokeLater(new Runnable(){
			public void run(){
				Toolkit tool1 = Toolkit.getDefaultToolkit();
				Dimension d1 = tool1.getScreenSize(); d1.width = 1200; d1.height = 800;
				
				Ramka1 rama1 = new Ramka1();
				rama1.setTitle("Funkcje Kwadratowe");
				rama1.setSize(d1);
				//rama1.getContentPane().setBackground(Color.RED);
				rama1.getContentPane().setForeground(Color.yellow);
				rama1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				rama1.setResizable(true);
				rama1.setUndecorated(false);
				rama1.setVisible(true);
			}
		});
  }
}

class Ramka1 extends JFrame{
	JPanel panel = new JPanel();
	
	JTextField aL = new JTextField(2);
	JTextField bL = new JTextField(2);
	JLabel al = new JLabel("a-Linia");
	JLabel bl = new JLabel("b-Linia");
	
	JTextField aK = new JTextField(2);
	JTextField bK = new JTextField(2);
	JTextField cK = new JTextField(2);
	JLabel ak = new JLabel("a-Kwadrat");
	JLabel bk = new JLabel("b-Kwadrat");
	JLabel ck = new JLabel("c-Kwadrat");
	
	JButton start = new JButton("start");
	JButton pobierz = new JButton("pobierz");
	
	int aLinia = 1;
	int bLinia = 1;
	
	int aKwadra = 1;
	int bKwadra = 1;
	int cKwadra = 1;
	
	public Ramka1(){
		panel.add(al); panel.add(aL); panel.add(bl); panel.add(bL);
		panel.add(ak); panel.add(aK); panel.add(bk);panel.add(bK);panel.add(ck);panel.add(cK);
		panel.add(start); 
		aL.setText("1"); bL.setText("1"); aK.setText("1"); bK.setText("1");cK.setText("1");
		this.getContentPane().add(panel,BorderLayout.NORTH);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				aLinia = Integer.parseInt(aL.getText());
				bLinia = Integer.parseInt(bL.getText());
				
				aKwadra = Integer.parseInt(aK.getText());
			    bKwadra = Integer.parseInt(bK.getText());
				cKwadra = Integer.parseInt(cK.getText());
				
				Panel pan = new Panel(aLinia,bLinia,aKwadra,bKwadra,cKwadra);
				
				//uruchomienie przelicznika podziałek
				pan.delta(Double.parseDouble(aK.getText()), Double.parseDouble(bK.getText()), Double.parseDouble(cK.getText()));
				pan.ustalf2();
				pan.ustalf();
				
			    SwingUtilities.invokeLater(new Runnable() {
			    	public void run() {
			    		add(pan,BorderLayout.CENTER);
						revalidate();
					    repaint();
			    	}
			    });
			}
		});
	}
}

class Panel extends JPanel{
	//parametry funkcji liniowej
	private static double aL = 0;
	private static double bL = 0;
	//parametry funkcji kwadratowej
	private static double aK = 0;
	private static double bK = 0;
	private static double cK = 0;
	
	private static double delta = 0;
	private static double x0 = 0;
	private static double x1 = 0;
	private static double x2 = 0;
	private static double p = 0;
	private static double q = 0;
	
	public Panel(long aL,long bL,long aK,long bK,long cK) {
		this.aL = aL; this.bL = bL;
		this.aK = aK; this.bK = bK; this.cK = cK;
	}
	
	public void ustalf(){
		this.aL = aL;
		this.bL = -bL*40;
	}
	
	public double f(double x){
		return aL*x+bL;
	}
	
	public void ustalf2(){
		aK = aK/40;
	    bK = bK;
	    cK = cK*40;
	}
	
	public double f2(double x){
		return aK*x*x+bK*x+cK;
	}
	
	public void delta(double a,double b,double c){
		
		delta = (bK*bK) - (4*aK*cK);
		if(delta == 0){
			x0 = -bK/(2*aK);
		}
		if(delta > 0 ){
			x1 = (-bK - Math.sqrt(delta))/(2*aK);
			x2 = (-bK + Math.sqrt(delta))/(2*aK);
		}
		p = -(bK/(2*aK))*40;
		q = (delta/(4*aK))*40;
	}
	
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		Line2D osx = new Line2D.Double(0,400,1200,400);  //os x
		Line2D osy = new Line2D.Double(600,0,600,800);   //os y
		g2.draw(osx);
		g2.draw(osy);
		//podziałka osi x dodatnia
		for(int i = 1200 ; i >= 0 ; i--){
			if( (i%40 == 0) ){
				if(i < 600){
					String v = Integer.toString(i/40);
					g.drawLine(i+600,400,i+600,403);
					g.drawString(v,i+600, 425);
				}
			}
		}
		
	    //podziałka osi x ujemna
		for(int i = 600 ; i > 0 ; i--){
			if( i%40 == 0){
				String v = Integer.toString(i/40);
				g.drawLine(i,400,i,403);
				g.drawString("-"+v,600-i,425);
			}
		}
		
		//podziałka osi y dodatnia
		for(int i = 800 ; i > 0 ; i--){
			if( (i%40 == 0) ){
				if(i <= 400){
					String v = Integer.toString(i/40);
					g.drawLine(600,i,603,i);
					g.drawString(v,610,400-i);
				}
			}
		}
		
		//podziałka osi y ujemna
		for(int i = 800 ; i > 0 ; i--){
			if( (i%40 == 0) ){
				if(i <= 400){
					String v = Integer.toString(i/40);
					g.drawLine(600,i+400,603,i+400);
					g.drawString("-"+v,610,i+400);
				}
			}
		}
		
		//rysowanie wykresu funkcji kwadratowej i liniowej
		for(double x=-600; x < 600; x=x+0.01){
			double yK = -f2(x);
			double yL = f(x);
			g.setColor(Color.red);
			g.drawLine((int)(x+600),(int)(yK+400),(int)(x+601),(int)(yK+400)); // wykres kwadrat oraz przesuni�cie 
			g.setColor(Color.BLUE);
			g.drawLine((int)x+600,(int)yL+400,(int)x+601,(int)yL+400); // wykres liniowy oraz przesuni�cie
			g.setColor(Color.green);
			g.drawLine((int)p+595,(int)q+400,(int)p+605,(int)q+400);
			g.drawLine((int)p+600,(int)q+395,(int)p+600,(int)q+405);
			
		}
	}
}
