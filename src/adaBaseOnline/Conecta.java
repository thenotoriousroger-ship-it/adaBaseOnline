package adaBaseOnline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			url1 = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0";
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
		
		public int getVendedorID(String nombre) {
			int idVend=0;
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
			     myRs= myStmt.executeQuery("SELECT id_vendedor FROM vendedor WHERE nombre ='"+nombre+"'");
			     if (myRs.next()) {
			            idVend = myRs.getInt("id_vendedor");
			        }
			     return idVend;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		
		public String getVendedorName(int id) {
			String nomVend = null;
			try {
				Connection   MyConn = DriverManager.getConnection(url1, user, password);
			     Statement myStmt  = MyConn.createStatement();
			     myRs= myStmt.executeQuery("SELECT nombre FROM vendedor WHERE id_vendedor ="+id);
			     if (myRs.next()) {
			            nomVend = myRs.getString("nombre");
			        }
			     return nomVend;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return nomVend;
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
		
		public boolean guardarPedido(String nombreCliente, String vendedor, double total, DefaultTableModel modeloTabla) {
		    Connection MyConn = null;
		    int idVend=getVendedorID(vendedor);
		    try {
		        MyConn = DriverManager.getConnection(url1, user, password);
		        MyConn.setAutoCommit(false); 

		        String clienteFinal = (nombreCliente != null && !nombreCliente.trim().isEmpty()) ? nombreCliente : "Público General";
		        String sqlCliente = "INSERT INTO clientes (nombre) VALUES (?) RETURNING id_cliente";
		        PreparedStatement stmtCliente = MyConn.prepareStatement(sqlCliente);
		        stmtCliente.setString(1, clienteFinal);
		        ResultSet rsCliente = stmtCliente.executeQuery();
		        int idCliente = 0;
		        if (rsCliente.next()) {
		            idCliente = rsCliente.getInt("id_cliente");
		        }

		        String sqlPedido = "INSERT INTO pedidos (id_cliente, id_vendedor, total) VALUES (?, ?, ?) RETURNING id_pedido";
		        PreparedStatement stmtPedido = MyConn.prepareStatement(sqlPedido);
		        stmtPedido.setInt(1, idCliente);
		        stmtPedido.setInt(2, idVend);
		        stmtPedido.setDouble(3, total);
		        ResultSet rsPedido = stmtPedido.executeQuery();
		        int idPedido = 0;
		        if (rsPedido.next()) {
		            idPedido = rsPedido.getInt("id_pedido");
		        }

		        String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, es_pastel, cantidad, subtotal) VALUES (?, ?, ?, ?, ?)";
		        PreparedStatement stmtDetalle = MyConn.prepareStatement(sqlDetalle);

		        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
		            int cantidad = (int) modeloTabla.getValueAt(i, 0);
		            double precio = (double) modeloTabla.getValueAt(i, 2);
		            int idProd = (int) modeloTabla.getValueAt(i, 3);
		            boolean esPastel = (boolean) modeloTabla.getValueAt(i, 4);
		            double subtotal = cantidad * precio;

		            stmtDetalle.setInt(1, idPedido);
		            stmtDetalle.setInt(2, idProd);
		            stmtDetalle.setBoolean(3, esPastel);
		            stmtDetalle.setInt(4, cantidad);
		            stmtDetalle.setDouble(5, subtotal);
		            stmtDetalle.executeUpdate(); 
		        }

		        MyConn.commit(); 
		        return true;

		    } catch (SQLException e) {
		        if (MyConn != null) {
		            try { MyConn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		        e.printStackTrace();
		        return false;
		    } finally {
		        if (MyConn != null) {
		            try { 
		                MyConn.setAutoCommit(true); 
		                MyConn.close(); 
		            } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		    }
		}
		
		public String getHistorialCliente(String nombreCliente) {
		    StringBuilder historial = new StringBuilder();
		    historial.append("Historial de compras de: ").append(nombreCliente).append("\n");
		    historial.append("========================================\n");
		    
		    try {
		        Connection MyConn = DriverManager.getConnection(url1, user, password);
		        String query = "SELECT p.fecha_pedido, p.total, p.id_vendedor " +
		                       "FROM pedidos p " +
		                       "INNER JOIN clientes c ON p.id_cliente = c.id_cliente " +
		                       "WHERE c.nombre = ? " +
		                       "ORDER BY p.fecha_pedido DESC"; 
		        PreparedStatement stmt = MyConn.prepareStatement(query);
		        stmt.setString(1, nombreCliente);
		        ResultSet rs = stmt.executeQuery();
		        
		        boolean tienePedidos = false;
		        double sumaTotal = 0.0;
		        
		        while(rs.next()) {
		            tienePedidos = true;
		            String fecha = rs.getString("fecha_pedido").substring(0, 10); 
		            double total = rs.getDouble("total");
		            String vend = getVendedorName(rs.getInt("id_vendedor"));
		            
		            historial.append("Fecha: ").append(fecha)
		                     .append(" | Total: $").append(String.format("%.2f", total))
		                     .append(" | Atendió: ").append(vend).append("\n");
		                     
		            sumaTotal += total;
		        }
		        
		        if (!tienePedidos) {
		            return "El cliente '" + nombreCliente + "' no tiene compras registradas o es nuevo.";
		        }
		        
		        historial.append("========================================\n");
		        historial.append("Total gastado históricamente: $").append(String.format("%.2f", sumaTotal));
		        
		        return historial.toString();
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return "Hubo un error al buscar el historial en la base de datos.";
		    }
		}
		

	

}