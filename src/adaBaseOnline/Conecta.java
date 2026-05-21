package adaBaseOnline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Conecta {
	private String url1 ;
	private  String user;
	private  String password ;
    private ResultSet myRs;


		public Conecta() {
			url1 = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres";
            user = "postgres.wpwlamrkrofpedgjdpoe";
            password = "SoloOpinoSinAyuda";
		}
		
		public ResultSet getMyRs(String tabla) {
			
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
		         myRs=  myStmt.executeQuery("select * from "+tabla);
		      
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return myRs;
		}
				
		public ResultSet getPastel(int id) {
			
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
		         myRs=  myStmt.executeQuery("SELECT * FROM pasteles WHERE id_pastel ="+id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
			}
			return myRs;
		}
		
		public ResultSet getProducto(int id) {
			
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
		         myRs=  myStmt.executeQuery("SELECT * FROM productos WHERE id ="+id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
			}
			return myRs;
		}
		public int getCant(int id) {
			int cant=0;
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
			    myRs= myStmt.executeQuery("SELECT cantidad FROM productos WHERE id =" + id);
			     if (myRs.next()) {
			            cant = (int) myRs.getFloat("cantidad");
			        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
			}
			return cant;
		}
		
		public boolean modificarCantidad(int id, int cant) {
			int CantOrig=getCant(id);
			int NuevoTotal=CantOrig+cant;
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
			     String oracion="UPDATE productos SET cantidad ="+NuevoTotal+" WHERE id ="+id;
		         myStmt.executeUpdate(oracion);
		         return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		public static class Producto {
		    private int id;
		    @SuppressWarnings("unused")
			private String nombreProd;
		    @SuppressWarnings("unused")
			private double precioProd;

		    public Producto(int id, String nombre, double precio) {
		        this.id = id;
		        this.nombreProd = nombre;
		        this.precioProd = precio;
		    }

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}
			@Override
		    public String toString() {
		        return nombreProd + " - $" + precioProd;
		    }
		}
		
		public void generarEImprimirPedido(DefaultTableModel modeloTabla, String nombre, String usuario, String total) {
		    String clienteText = (nombre != null && !nombre.trim().isEmpty()) ? nombre : "Público General";
		    String vendedorText = usuario; 
		    String totalText = total;
		    java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();
		    job.setJobName("Pedido - " + clienteText);
		    job.setPrintable(new java.awt.print.Printable() {
		        @Override
		        public int print(java.awt.Graphics graphics, java.awt.print.PageFormat pageFormat, int pageIndex) throws java.awt.print.PrinterException {
		
		            if (pageIndex > 0) {return NO_SUCH_PAGE; }
		            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
		            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		            g2d.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 10));
		            int y = 20;
		           
		            g2d.drawString("=========================================", 10, y); y += 15;
		            g2d.drawString("       DANNY DESSERTS AND MORE           ", 10, y); y += 15;
		            g2d.drawString("=========================================", 10, y); y += 20;
		            g2d.drawString("Cliente: " + clienteText, 10, y); y += 15;
		            g2d.drawString("Atendió: " + vendedorText, 10, y); y += 15;
		            g2d.drawString("-----------------------------------------", 10, y); y += 15;
		            g2d.drawString("Cant  | Producto               | Precio  ", 10, y); y += 15;
		            g2d.drawString("-----------------------------------------", 10, y); y += 15;
		            
		           
		            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
		                int cantidad = (int) modeloTabla.getValueAt(i, 0);
		                String producto = (String) modeloTabla.getValueAt(i, 1);
		                double precio = (double) modeloTabla.getValueAt(i, 2);
		                
		                if (producto.length() > 22) {
		                    producto = producto.substring(0, 19) + "...";
		                }
		                
		              
		                String linea = String.format("%-5d | %-22s | $%6.2f", cantidad, producto, precio);
		                g2d.drawString(linea, 10, y);
		                y += 15; 
		            }
		            
		            g2d.drawString("-----------------------------------------", 10, y); y += 15;
		            g2d.drawString("TOTAL: " + totalText, 10, y); y += 15;
		            g2d.drawString("=========================================", 10, y); y += 15;
		            g2d.drawString("      ¡Gracias por su preferencia!       ", 10, y);
		            
		            return PAGE_EXISTS;
		        }
		    });

		  
		    if (job.printDialog()) {
		        try {
		            job.print(); 
		        } catch (java.awt.print.PrinterException e) {
		            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
		            e.printStackTrace();
		        }
		    }
		}
		
		

	

}