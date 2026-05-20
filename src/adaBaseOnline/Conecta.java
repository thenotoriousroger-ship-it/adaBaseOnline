package adaBaseOnline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		
		

	

}