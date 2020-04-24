import javax.swing.JFrame;
import javax.swing.JLabel;

public class Imprimir extends JFrame{
	public static JLabel Status;
	public static JLabel Barras;
	public int Tiempo;
	public Imprimir() {
		this.setTitle("Imprimir");
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		
		Status=new JLabel("Imprimiendo");
		Barras=new JLabel("l");
		
		Status.setBounds(180, 5, 200, 100);
		Status.setFont(new java.awt.Font("Arial", 1, 20));
		Barras.setBounds(80, 50, 500, 100);
		Barras.setFont(new java.awt.Font("Arial", 1, 20));
		
		this.add(Status);
		this.add(Barras);

	}
	public static void main(String args[]) {
		new Imprimir().setVisible(true);
		HiloImprimir Hilo1=new HiloImprimir(Status, Barras);
		Hilo1.start();
		}
	}

