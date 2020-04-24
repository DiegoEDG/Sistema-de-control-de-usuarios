package Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Log {
	FileWriter Texto;

	public Log() {
		// TODO Auto-generated constructor stub
	}
	
	public Log(String operacion) throws IOException {
		Texto=new FileWriter(new File("Transacciones.txt"),true);
		Calendar Fecha=Calendar.getInstance();
		Texto.write((String.valueOf(Fecha.get(Calendar.DAY_OF_MONTH))
				+"/"+String.valueOf(Fecha.get(Calendar.MONTH)+1)
				+"/"+String.valueOf(Fecha.get(Calendar.YEAR))
				+"		"+String.valueOf(Fecha.get(Calendar.HOUR_OF_DAY))
				+":"+String.valueOf(Fecha.get(Calendar.MINUTE)))+operacion+"\r\n");
		Texto.close();
	}

	public void Log(String string) {
		// TODO Auto-generated method stub
		
	}
}