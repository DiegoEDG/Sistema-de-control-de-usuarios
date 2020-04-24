import javax.swing.JLabel;

public class HiloImprimir extends Thread{
	public JLabel Status2,Barras2;
	public HiloImprimir(JLabel Status, JLabel Barras) {
		this.Status2=Status;
		this.Barras2=Barras;
	}
	public void run() {
		int Segundos=0;
		int Minutos=0;
		
		for(int i=0;i<=60;i++) {
			try {
				this.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Segundos++;
			Barras2.setText(Barras2.getText()+" l");
			
			if(i==30) {
				Minutos++;
				Segundos=0;
				i=0;
				Barras2.setText("l");
				
				if(Minutos==5) {
					Status2.setText("Listo!");
					Barras2.setText(null);
					break;
				}
			}
		}
	}

}
