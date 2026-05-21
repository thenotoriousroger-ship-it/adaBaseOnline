package adaBaseOnline;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Ventas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String usuario;
	private JTable table;
	private String nombre;
	private DefaultTableModel modeloTabla;
	private Conecta conexionBD = new Conecta();
	private JLabel lblTotal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventas frame = new Ventas("invitado");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventas(String usr) {
		this.usuario=usr;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido "+usuario);
		lblNewLabel.setBounds(10, 11, 200, 14);
		contentPane.add(lblNewLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.setBounds(0, 36, 112, 214);
		contentPane.add(toolBar);
		
		JLabel lblNewLabel_3 = new JLabel("esto es un easter egg");
		lblNewLabel_3.setBounds(154, 22, 140, 14);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);
		
		JButton btnNewButton = new JButton("Nueva Venta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nombre=JOptionPane.showInputDialog("nombre del cliente");
				lblNewLabel_3.setText("Venta de "+nombre);
				lblNewLabel_3.setVisible(true);
				btnNewButton.setEnabled(false);
			}
		});
		toolBar.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Ver cliente");
		toolBar.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("Nuevo Ítem");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoDeProducto nuevaVentana = new TipoDeProducto(Ventas.this);
		        nuevaVentana.setVisible(true);
			}
		});
		toolBar.add(btnNewButton_1);
		
		JButton btnBorrar = new JButton("Borrar ítem");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = table.getSelectedRow();
		        if (filaSeleccionada >= 0) {
		            modeloTabla.removeRow(filaSeleccionada);
		            actualizarTotal();
		        } else {
		            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto de la tabla para borrar.");
		        }
			}
		});
		toolBar.add(btnBorrar);
		
		JButton btnNewButton_3 = new JButton("Cobrar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 if (modeloTabla.getRowCount() == 0) {JOptionPane.showMessageDialog(null, "No hay productos en el carrito para cobrar.");
		            return;}
		     int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas proceder con el cobro e imprimir el pedido?", "Confirmar Venta", JOptionPane.YES_NO_OPTION);
		     if (respuesta == JOptionPane.YES_OPTION) {
		        conexionBD.generarEImprimirPedido(modeloTabla, nombre, usuario, lblTotal.getText());
		        modeloTabla.setRowCount(0);
	            actualizarTotal();        
		            btnNewButton.setEnabled(true);
		            lblNewLabel_3.setVisible(false);
		            nombre = null; 
		            
		            JOptionPane.showMessageDialog(null, "Venta procesada y PDF enviado a impresión.");
		        }
		    }
		});
		toolBar.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Editar existencias");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarExistencias nuevaVentana = new AgregarExistencias();
				nuevaVentana.setVisible(true);
			}
		});
		toolBar.add(btnNewButton_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(122, 36, 456, 206);
		contentPane.add(scrollPane);
		
		String[] columnas = {"Cantidad", "Producto", "Precio", "ID", "Producto"};
		modeloTabla = new DefaultTableModel(columnas, 0);
		table = new JTable(modeloTabla);
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setWidth(0);
		scrollPane.setViewportView(table);
		
		lblTotal = new JLabel("$");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotal.setBounds(453, 243, 125, 45);
		contentPane.add(lblTotal);
		
		JLabel lblNewLabel_2 = new JLabel("TOTAL:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_2.setBounds(382, 253, 61, 24);
		contentPane.add(lblNewLabel_2);
		

	}
	
	public void recibirProductoSeleccionado(int id, boolean prod) {
		int CantidadProducto = 0;
		String nombreProducto = null;
		double precioProducto = 0;
		ResultSet Producto;
		if (prod==true) {
			Producto=conexionBD.getPastel(id);
			CantidadProducto=1;
			try {
				if (Producto != null && Producto.next()) {
	                nombreProducto = Producto.getString("nombre");
	                precioProducto = Producto.getDouble("precio");
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			Producto=conexionBD.getProducto(id);
			CantidadProducto=1;
			try {
				if (Producto != null && Producto.next()) {
	                nombreProducto = Producto.getString("nombre");
	                precioProducto = Producto.getDouble("precio");
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Object[] nuevaFila = {CantidadProducto, nombreProducto, precioProducto, id, prod};
		modeloTabla.addRow(nuevaFila);
		actualizarTotal();
    }
	
	private void actualizarTotal() {
	    double sumaTotal = 0.0;
	    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
	        double precio = (double) modeloTabla.getValueAt(i, 2);
	        sumaTotal += precio;
	    }
	    lblTotal.setText("$ " + String.format("%.2f", sumaTotal));
	}
      
}
