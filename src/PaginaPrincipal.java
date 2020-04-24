import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.ResultSetMetaData;

import Log.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import SQLConnection.SQLConnection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaginaPrincipal  extends JFrame{
	SQLConnection conn = new SQLConnection();
	Connection con = conn.Conn();
	
	public DefaultTableModel Modelo,Modelo2;
	public JTable Tabla,Tabla2;
	public JScrollPane Scroll,Scroll2;
	public JButton Undo,Redo,Reporte,Imprimir,Salir;
	public JLabel IDL,NombreL,EdadL,TelefonoL,DireccionL,NivelL,DiasL,HorarioL,ProfesorL;
	public JTextField ID,Nombre,Edad,Telefono,Direccion;
	public JComboBox<String> Nivel,Dias,Horario,Profesor;
	public JButton Registrar,Eliminar,Modificar,Limpiar;
	
	int IndexFila,Contador=0;
	boolean Campos;
	static boolean Cerrar=false;
	Object[] DatosFila=new Object[9];
	Stack PilaPS=new Stack();
	Stack PilaPS2=new Stack();
	Stack PilaRN=new Stack();
	Stack PilaRN2=new Stack();
	Stack PilaE=new Stack();
	Stack PilaDO=new Stack();
	Stack PilaDM=new Stack();
	Stack PilaF=new Stack();
	Stack PilaF2=new Stack();
	Stack<String> PilaA=new Stack<String>();
	Stack<String> PilaA2=new Stack<String>();

	public PaginaPrincipal() {
		this.setTitle("Sistema de control de usuarios");
		this.setSize(680, 620);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);

		Modelo=new DefaultTableModel();
		Scroll=new JScrollPane();
		Tabla=new JTable();

		Modelo.addColumn("ID");
		Modelo.addColumn("Nombre");
		Modelo.addColumn("Edad");
		Modelo.addColumn("Telefono");
		Modelo.addColumn("Direccion");
		Modelo.addColumn("Nivel");
		Modelo.addColumn("Dias");
		Modelo.addColumn("Horario");
		Modelo.addColumn("Profesor");

		try {
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from usuarios");

			while (rs.next()) {
				Object[] Datos = new Object[9];
				for (int i = 0; i < 9; i++) {
					Datos[i] = rs.getObject(i + 1);
				}
				Modelo.addRow(Datos);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		Tabla.setModel(Modelo);
		Tabla.getTableHeader().setReorderingAllowed(true);
		Tabla.getTableHeader().setResizingAllowed(true);
		Tabla.setIntercellSpacing(new Dimension(4, 4));
		Tabla.setShowGrid(true);
		Tabla.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int Fila=Tabla.getSelectedRow();
				String[] Datos=new String[9];

				Datos[0]=Tabla.getValueAt(Fila,0).toString();
				Datos[1]=Tabla.getValueAt(Fila,1).toString();
				Datos[2]=Tabla.getValueAt(Fila,2).toString();
				Datos[3]=Tabla.getValueAt(Fila,3).toString();
				Datos[4]=Tabla.getValueAt(Fila,4).toString();
				Datos[5]=Tabla.getValueAt(Fila,5).toString();
				Datos[6]=Tabla.getValueAt(Fila,6).toString();
				Datos[7]=Tabla.getValueAt(Fila,7).toString();
				Datos[8]=Tabla.getValueAt(Fila,8).toString();

				ID.setText(Datos[0]); 
				Nombre.setText(Datos[1]);
				Edad.setText(Datos[2]);
				Telefono.setText(Datos[3]);
				Direccion.setText(Datos[4]);

				if(Datos[5].contentEquals("Basico")) {
					Nivel.setSelectedIndex(1);
				}else if(Datos[5].contentEquals("Intermedio")) {
					Nivel.setSelectedIndex(2);
				}else if(Datos[5].contentEquals("Avanzado")) {
					Nivel.setSelectedIndex(3);
				}

				if(Datos[6].equals("Sabados")) {
					Dias.setSelectedIndex(1);
				}else if(Datos[6].equals("Domingos")) {
					Dias.setSelectedIndex(2);
				}else if(Datos[6].equals("Sabados y Domingos")) {
					Dias.setSelectedIndex(3);
				}

				if(Datos[7].equals("6-7")) {
					Horario.setSelectedIndex(1);
				}else if(Datos[7].equals("7-8")) {
					Horario.setSelectedIndex(2);
				}else if(Datos[7].equals("8-9")) {
					Horario.setSelectedIndex(3);
				}else if(Datos[7].equals("9-10")) {
					Horario.setSelectedIndex(4);
				}else if(Datos[7].equals("10-11")) {
					Horario.setSelectedIndex(5);
				}else if(Datos[7].equals("11-12")) {
					Horario.setSelectedIndex(6);
				}else if(Datos[7].equals("12-13")) {
					Horario.setSelectedIndex(7);
				}else if(Datos[7].equals("13-14")) {
					Horario.setSelectedIndex(8);
				}

				if(Datos[8].equals("Juan")) {
					Profesor.setSelectedItem("Juan");
				}else if(Datos[8].equals("Manuel")) {
					Profesor.setSelectedIndex(2);
				}else if(Datos[8].equals("Santiago")) {
					Profesor.setSelectedIndex(3);
				}
			}
		});

		Scroll = new JScrollPane(Tabla);
		Scroll.setBounds(10, 50, 650, 400);
		Scroll.setViewportView(Tabla);
		this.add(Scroll);

		Undo=new JButton("Deshacer");
		Redo=new JButton("Rehacer");
		Reporte=new JButton("Reporte");
		Imprimir=new JButton("Imprimir");
		Salir=new JButton("Salir");
		Undo.setBounds(10, 10, 100, 30);
		Undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(PilaA.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No hay acciones a realizar");
					}else {
						String Accion=(String)PilaA.peek();
						System.out.println(Accion);
						switch (Accion) {
						case "Registrar":
							
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());
							PilaRN2.push(PilaRN.pop());

							System.out.println("Pila RegistroNuevo2 --->"+PilaRN2);
							
							Modelo.removeRow(Tabla.getRowCount()-1);
							
							PilaPS2.push(PilaPS.pop());
							PilaA2.push(PilaA.pop());
							System.out.println("Pila Accion2 --->"+PilaA2);
							System.out.println("Pila PreparedStatement2 -->"+PilaPS2);
						break;
						case "Eliminar":
							Object[] DatosE=new Object[9];
							DatosE[8]=PilaE.pop();
							DatosE[7]=PilaE.pop();
							DatosE[6]=PilaE.pop();
							DatosE[5]=PilaE.pop();
							DatosE[4]=PilaE.pop();
							DatosE[3]=PilaE.pop();
							DatosE[2]=PilaE.pop();
							DatosE[1]=PilaE.pop();
							DatosE[0]=PilaE.pop();
							
							Modelo.insertRow((int) PilaF.peek(), DatosE);
							
							PilaF2.push(PilaF.pop());
							PilaPS2.push(PilaPS.pop());
							PilaA2.push(PilaA.pop());
							System.out.println("Pila Fila2 --->"+PilaF2);
							System.out.println("Pila Accion2 --->"+PilaA2);
							System.out.println("Pila PreparedStatement2 -->"+PilaPS2);
						break;
						case "Modificar":
							
							Object[] DatosO=new Object[9];
							DatosO[8]=PilaDO.pop();
							DatosO[7]=PilaDO.pop();
							DatosO[6]=PilaDO.pop();
							DatosO[5]=PilaDO.pop();
							DatosO[4]=PilaDO.pop();
							DatosO[3]=PilaDO.pop();
							DatosO[2]=PilaDO.pop();
							DatosO[1]=PilaDO.pop();
							DatosO[0]=PilaDO.pop();
							
							Modelo.removeRow((int)PilaF.peek());
							Modelo.insertRow((int)PilaF.peek(),DatosO);
							
							PilaF2.push(PilaF.pop());
							PilaPS2.push(PilaPS.pop());
							PilaA2.push(PilaA.pop());
							System.out.println("Pila Fila2 --->"+PilaF2);
							System.out.println("Pila Accion2 --->"+PilaA2);
							System.out.println("Pila PreparedStatement2 -->"+PilaPS2);
						break;
						}
					}
					
					try {
						String MensajeLogR="		Se realizó la acción deshacer";
						Log LogR=new Log(MensajeLogR);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}	
			}
		});
		Redo.setBounds(120, 10, 100, 30);
		Redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(PilaA2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No hay acciones a realizar");
				}else {
					String Accion=(String)PilaA2.peek();
					System.out.println(Accion);
					
					switch (Accion) {
					case "Registrar":
						
						Object[] DatosNR=new Object[9];
						DatosNR[0]=PilaRN2.pop();
						DatosNR[1]=PilaRN2.pop();
						DatosNR[2]=PilaRN2.pop();
						DatosNR[3]=PilaRN2.pop();
						DatosNR[4]=PilaRN2.pop();
						DatosNR[5]=PilaRN2.pop();
						DatosNR[6]=PilaRN2.pop();
						DatosNR[7]=PilaRN2.pop();
						DatosNR[8]=PilaRN2.pop();
						
						System.out.println(DatosNR);
						Modelo.addRow(DatosNR);
						
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 0));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 1));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 2));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 3));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 4));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 5));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 6));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 7));
						PilaRN.push(Tabla.getValueAt(Tabla.getRowCount() - 1, 8));
						
						PilaPS.push(PilaPS2.pop());
						PilaA.push(PilaA2.pop());
						System.out.println("Pila con Accion --->"+PilaA);
						System.out.println("Pila con PreparedStatement -->"+PilaPS);
					break;
					case "Eliminar":
						int NumFila2=(int) PilaF2.peek();
						
						PilaE.push(Tabla.getValueAt(NumFila2, 0));
						PilaE.push(Tabla.getValueAt(NumFila2, 1));
						PilaE.push(Tabla.getValueAt(NumFila2, 2));
						PilaE.push(Tabla.getValueAt(NumFila2, 3));
						PilaE.push(Tabla.getValueAt(NumFila2, 4));
						PilaE.push(Tabla.getValueAt(NumFila2, 5));
						PilaE.push(Tabla.getValueAt(NumFila2, 6));
						PilaE.push(Tabla.getValueAt(NumFila2, 7));
						PilaE.push(Tabla.getValueAt(NumFila2, 8));
						
						Modelo.removeRow(NumFila2);
						
						PilaF.push(PilaF2.pop());
						PilaPS.push(PilaPS2.pop());
						PilaA.push(PilaA2.pop());
						System.out.println("Pila con numero Fila --->"+PilaF);
						System.out.println("Pila con Accion2 --->"+PilaA);
						System.out.println("Pila con PreparedStatement2 -->"+PilaPS);
					break;
					case "Modificar":
						int NumFila=(int)PilaF2.peek();
						
						PilaDO.push(Tabla.getValueAt(NumFila, 0));
						PilaDO.push(Tabla.getValueAt(NumFila, 1));
						PilaDO.push(Tabla.getValueAt(NumFila, 2));
						PilaDO.push(Tabla.getValueAt(NumFila, 3));
						PilaDO.push(Tabla.getValueAt(NumFila, 4));
						PilaDO.push(Tabla.getValueAt(NumFila, 5));
						PilaDO.push(Tabla.getValueAt(NumFila, 6));
						PilaDO.push(Tabla.getValueAt(NumFila, 7));
						PilaDO.push(Tabla.getValueAt(NumFila, 8));
						
						Object[] DatosM=new Object[9];
						DatosM[8]=PilaDM.pop();
						DatosM[7]=PilaDM.pop();
						DatosM[6]=PilaDM.pop();
						DatosM[5]=PilaDM.pop();
						DatosM[4]=PilaDM.pop();
						DatosM[3]=PilaDM.pop();
						DatosM[2]=PilaDM.pop();
						DatosM[1]=PilaDM.pop();
						DatosM[0]=PilaDM.pop();
						
						PilaDM.push(DatosM[0]);
						PilaDM.push(DatosM[1]);
						PilaDM.push(DatosM[2]);
						PilaDM.push(DatosM[3]);
						PilaDM.push(DatosM[4]);
						PilaDM.push(DatosM[5]);
						PilaDM.push(DatosM[6]);
						PilaDM.push(DatosM[7]);
						PilaDM.push(DatosM[8]);
						
						Modelo.removeRow(NumFila);
						Modelo.insertRow(NumFila,DatosM);
						
						PilaF.push(PilaF2.pop());
						PilaPS.push(PilaPS2.pop());
						PilaA.push(PilaA2.pop());
						System.out.println("Pila con numero Fila --->"+PilaF);
						System.out.println("Pila con Accion2 --->"+PilaA);
						System.out.println("Pila con PreparedStatement2 -->"+PilaPS);
					break;
					}
				}
				
					try {
						String MensajeLogR="		Se realizó la acción rehacer";
						Log LogR=new Log(MensajeLogR);
					} catch (IOException e1) {

						e1.printStackTrace();
					}     
			}
		});
		Reporte.setBounds(230, 10, 100, 30);
		Reporte.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				Document PDF=new Document();
				try {
					String dir=System.getProperty("user.home");
					PdfWriter.getInstance(PDF, new FileOutputStream(dir+"/Desktop/Reporte.pdf"));
					PDF.open();

					PdfPTable TablaPDF=new PdfPTable(9);

					TablaPDF.addCell("ID");
					TablaPDF.addCell("Nombre");
					TablaPDF.addCell("Edad");
					TablaPDF.addCell("Telefono");
					TablaPDF.addCell("Direccion");
					TablaPDF.addCell("Nivel");
					TablaPDF.addCell("Dias");
					TablaPDF.addCell("Horario");
					TablaPDF.addCell("Profesor");

					try {
						java.sql.Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("Select * from usuarios");

						while(rs.next()) {
							TablaPDF.addCell(rs.getString(1));
							TablaPDF.addCell(rs.getString(2));
							TablaPDF.addCell(rs.getString(3));
							TablaPDF.addCell(rs.getString(4));
							TablaPDF.addCell(rs.getString(5));
							TablaPDF.addCell(rs.getString(6));
							TablaPDF.addCell(rs.getString(7));
							TablaPDF.addCell(rs.getString(8));
							TablaPDF.addCell(rs.getString(9));
						}
						PDF.add(TablaPDF);
					}catch(DocumentException  | SQLException e2) {
						e2.printStackTrace();
					}
					PDF.close();
					JOptionPane.showMessageDialog(null, "Se genero el reporte en el escritorio");
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}            
		});
		Imprimir.setBounds(340, 10, 100, 30);
		Imprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Imprimir Impresion=new Imprimir();
				Impresion.main(null);
			}
		});

		Salir.setBounds(560, 10, 100, 30);
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PilaPS.isEmpty()) {
					dispose();
				}else {
					try {
						PreparedStatement psf;
						
						psf = (PreparedStatement) PilaPS.peek();
						psf.execute();
						dispose();
					} catch (SQLException ex) {
						System.out.println(ex);
					}			
				}
			}
		});
		
		this.add(Undo);
		this.add(Redo);
		this.add(Reporte);
		this.add(Imprimir);
		this.add(Salir);

		IDL=new JLabel("ID");
		NombreL=new JLabel("Nombre");
		EdadL=new JLabel("Edad");
		TelefonoL=new JLabel("Telefono");
		DireccionL=new JLabel("Direccion");
		NivelL=new JLabel("Nivel");
		DiasL=new JLabel("Dias");
		HorarioL=new JLabel("Horario");
		ProfesorL=new JLabel("Profesor");
		IDL.setBounds(10, 415, 50, 100);
		NombreL.setBounds(70, 415, 50, 100);
		EdadL.setBounds(280, 415, 50, 100);
		TelefonoL.setBounds(340, 415, 100, 100);
		DireccionL.setBounds(450, 415, 100, 100);
		NivelL.setBounds(10, 510, 50, 30);
		DiasL.setBounds(170, 510, 50, 30);
		HorarioL.setBounds(330, 510, 50, 30);
		ProfesorL.setBounds(490, 510, 50, 30);
		this.add(IDL);
		this.add(NombreL);
		this.add(EdadL);
		this.add(TelefonoL);
		this.add(DireccionL);
		this.add(NivelL);
		this.add(DiasL);
		this.add(HorarioL);
		this.add(ProfesorL);

		ID=new JTextField();
		Nombre=new JTextField();
		Edad=new JTextField();
		Telefono=new JTextField();
		Direccion=new JTextField();
		ID.setBounds(10, 475, 50, 30);
		ID.disable();
		Nombre.setBounds(70, 475, 200, 30);
		Edad.setBounds(280, 475 , 50, 30);
		Telefono.setBounds(340, 475 , 100, 30);
		Direccion.setBounds(450, 475 , 210, 30);
		this.add(ID);
		this.add(Nombre);
		this.add(Edad);
		this.add(Telefono);
		this.add(Direccion);

		Nivel=new JComboBox<String>();
		Nivel.addItem("------");
		Nivel.addItem("Basico");
		Nivel.addItem("Intermedio");
		Nivel.addItem("Avanzado");
		Dias=new JComboBox<String>();
		Dias.addItem("------");
		Dias.addItem("Sabados");
		Dias.addItem("Domingos");
		Dias.addItem("Sabados y Domingos");
		Horario=new JComboBox<String>();
		Horario.addItem("------");
		Horario.addItem("6-7");
		Horario.addItem("7-8");
		Horario.addItem("8-9");
		Horario.addItem("9-10");
		Horario.addItem("10-11");
		Horario.addItem("11-12");
		Horario.addItem("12-13");
		Horario.addItem("13-14");
		Profesor=new JComboBox<String>();
		Profesor.addItem("------");
		Profesor.addItem("Juan");
		Profesor.addItem("Manuel");
		Profesor.addItem("Santiago");
		Nivel.setBounds(10, 535, 150, 30);
		Dias.setBounds(170, 535, 150, 30);
		Horario.setBounds(330, 535, 150, 30);
		Profesor.setBounds(490, 535, 170, 30);
		this.add(Nivel);
		this.add(Dias);
		this.add(Horario);
		this.add(Profesor);

		Registrar=new JButton("Registrar");
		Eliminar=new JButton("Eliminar");
		Modificar=new JButton("Modificar");
		Limpiar=new JButton("Limpiar");
		Registrar.setBounds(10, 575, 100, 30);
		Registrar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				if(ValidarCampos()==true) {
					JOptionPane.showMessageDialog(null, "Tienes que llenar todos los campos para registrar al usuario");					
				}else {
					try {
						PreparedStatement psr=null;                     
						String Insert=("INSERT INTO usuarios (nombre,edad,telefono,direccion,nivel,dias,horario,profesor) VALUES (?,?,?,?,?,?,?,?)");
						psr=con.prepareStatement(Insert);

						psr.setString(1,Nombre.getText());
						psr.setString(2,Edad.getText());
						psr.setString(3,Telefono.getText());
						psr.setString(4,Direccion.getText());
						psr.setString(5, (String) Nivel.getSelectedItem());
						psr.setString(6, (String) Dias.getSelectedItem());
						psr.setString(7, (String) Horario.getSelectedItem());
						psr.setString(8, (String) Profesor.getSelectedItem());
						
						PilaPS.push(psr);
						System.out.println("Pila de los PreparedStatements -->"+PilaPS);
						
						PilaRN.push(ID.getText());
						PilaRN.push(Nombre.getText());
						PilaRN.push(Edad.getText());
						PilaRN.push(Telefono.getText());
						PilaRN.push(Direccion.getText());
						PilaRN.push((String) Nivel.getSelectedItem());
						PilaRN.push((String) Dias.getSelectedItem());
						PilaRN.push((String) Horario.getSelectedItem());
						PilaRN.push((String) Profesor.getSelectedItem());
						
						System.out.println("Pila con datos del NuevoRegistro --->"+PilaRN);
						
						Object id= Modelo.getValueAt(Modelo.getRowCount()-1, 0);
						int id2=(int)id+1;
						
						Object [] Datos ={id2,Nombre.getText(),Edad.getText(),Telefono.getText(),Direccion.getText(),Nivel.getSelectedItem(),Dias.getSelectedItem(),Horario.getSelectedItem(),Profesor.getSelectedItem()};
						Modelo.addRow(Datos);
		
						JOptionPane.showMessageDialog(null,"Usuario registrado correctamente");
						PilaA.push("Registrar");
						System.out.println("Pila de Accion --->"+PilaA);
						Limpiar();

					} catch (SQLException ex) {
						System.out.println(ex);
						JOptionPane.showMessageDialog(null,"No se pudo insertar el registro");
					}

					try {
						String MensajeLogR="		Se registró un cliente ";
						Log LogR=new Log(MensajeLogR);
					} catch (IOException e1) {

						e1.printStackTrace();
					}     
				}
			}
		});
		Eliminar.setBounds(120, 575, 100, 30);
		Eliminar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				try {               
					PreparedStatement pse=null;
					String Borrar="Delete from usuarios where id=?";
					pse=con.prepareStatement(Borrar);
					pse.setString(1,ID.getText());
					
					PilaPS.push(pse);
					System.out.println("Pila de los PreparedStatements -->"+PilaPS);
					
					PilaE.push(ID.getText());
					PilaE.push(Nombre.getText());
					PilaE.push(Edad.getText());
					PilaE.push(Telefono.getText());
					PilaE.push(Direccion.getText());
					PilaE.push((String) Nivel.getSelectedItem());
					PilaE.push((String) Dias.getSelectedItem());
					PilaE.push((String) Horario.getSelectedItem());
					PilaE.push((String) Profesor.getSelectedItem());
					System.out.println("Pila de datos Eliminados --->"+PilaE);
					
					int NumFila=Tabla.getSelectedRow();
					Modelo.removeRow(NumFila);
					PilaF.push(NumFila);
					System.out.println("Pila con NumeroFila --->"+PilaF);
					
					JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");
					PilaA.push("Eliminar");
					System.out.println("Pila de Accion --->"+PilaA);
					Limpiar();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "No se ha podido eliminar el usuario, asegurese de que el id es el correcto");
					e1.printStackTrace();
				}

				try {
					String MensajeLogE="		Se eliminó un cliente";
					Log LogE=new Log(MensajeLogE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Modificar.setBounds(230, 575, 100, 30);
		Modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ValidarCampos()==true) {
					JOptionPane.showMessageDialog(null, "Tienes que llenar todos los campos para registrar al usuario");					
				}else {
				try {
					PreparedStatement psm=null;
					String Actualizar = ("UPDATE usuarios set nombre=?,edad=?,telefono=?,direccion=?,nivel=?,dias=?,horario=?,profesor=? WHERE id=?");
					psm = con.prepareStatement(Actualizar);

					psm.setString(1, Nombre.getText());
					psm.setString(2, Edad.getText());
					psm.setString(3, Telefono.getText());
					psm.setString(4, Direccion.getText());
					psm.setString(5, Nivel.getSelectedItem().toString());
					psm.setString(6, Dias.getSelectedItem().toString());
					psm.setString(7, Horario.getSelectedItem().toString());
					psm.setString(8, Profesor.getSelectedItem().toString());
					psm.setString(9, ID.getText());

					PilaPS.push(psm);
					System.out.println("Pila de los PreparedStatements -->"+PilaPS);
					
					IndexFila=Tabla.getSelectedRow();
					
					PilaDO.push(Modelo.getValueAt(IndexFila, 0));
					PilaDO.push(Modelo.getValueAt(IndexFila, 1));
					PilaDO.push(Modelo.getValueAt(IndexFila, 2));
					PilaDO.push(Modelo.getValueAt(IndexFila, 3));
					PilaDO.push(Modelo.getValueAt(IndexFila, 4));
					PilaDO.push(Modelo.getValueAt(IndexFila, 5));
					PilaDO.push(Modelo.getValueAt(IndexFila, 6));
					PilaDO.push(Modelo.getValueAt(IndexFila, 7));
					PilaDO.push(Modelo.getValueAt(IndexFila, 8));
					System.out.println("Pila con DatosOriginales --->"+PilaDO);
					
					Modelo.removeRow(IndexFila);
					Object [] datosA ={ID.getText(),Nombre.getText(),Edad.getText(),Telefono.getText(),Direccion.getText(),Nivel.getSelectedItem(),Dias.getSelectedItem(),Horario.getSelectedItem(),Profesor.getSelectedItem()};
					Modelo.insertRow(IndexFila,datosA);
					
					PilaDM.push(ID.getText());
					PilaDM.push(Nombre.getText());
					PilaDM.push(Edad.getText());
					PilaDM.push(Telefono.getText());
					PilaDM.push(Direccion.getText());
					PilaDM.push(Nivel.getSelectedItem().toString());
					PilaDM.push(Horario.getSelectedItem().toString());
					PilaDM.push(Dias.getSelectedItem().toString());
					PilaDM.push(Profesor.getSelectedItem().toString());	
					System.out.println("Pila con DatosModificados --->"+PilaDM);
					
					PilaF.push(IndexFila);
					System.out.println("Pila con el Index de la Fila --->"+PilaF);
					
					JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
					PilaA.push("Modificar");
					System.out.println("Pila de Accion --->"+PilaA);
					Limpiar();

				} catch (SQLException ex) {
					System.out.println(ex);
					JOptionPane.showMessageDialog(null,"No se pudieron actualizar los datos");
				}

				try {
					String MensajeLogM="		Se modificó un cliente";
					Log LogM=new Log(MensajeLogM);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				}
			}
		});
		Limpiar.setBounds(340, 575, 100, 30);
		Limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Limpiar();
			}
		});
		this.add(Registrar);
		this.add(Eliminar);
		this.add(Modificar);
		this.add(Limpiar);
	}
	
	public boolean ValidarCampos() {
		if(ID.getText().equals(null)||Nombre.getText().equals(null)||Edad.getText().equals(null)||Telefono.getText().equals(null)||Direccion.getText().equals(null)||Nivel.getSelectedIndex()==0||Dias.getSelectedIndex()==0||Horario.getSelectedIndex()==0||Profesor.getSelectedIndex()==0) {
			Campos=true;
		}
		return Campos;		
	}
	
	public void Limpiar() {
		ID.setText(null);
		Nombre.setText(null);
		Edad.setText(null);
		Telefono.setText(null);
		Direccion.setText(null);
		Nivel.setSelectedIndex(0);
		Dias.setSelectedIndex(0);
		Horario.setSelectedIndex(0);
		Profesor.setSelectedIndex(0);		
	}

	public static void main(String args[]){
		new PaginaPrincipal().setVisible(true);
		
	}
}