package adaBaseOnline;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adaBaseOnline.Conecta.Producto;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarExistencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	DefaultListModel modeloLista = new DefaultListModel<>();
	private Conecta conexionBD = new Conecta();
	ResultSet R;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgregarExistencias frame = new AgregarExistencias();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AgregarExistencias() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Postres");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		@SuppressWarnings({ })
		JList list = new JList(modeloLista);
		list.setBounds(10, 36, 319, 214);
		contentPane.add(list);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		comboBox.setBounds(338, 70, 86, 37);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue() != null) {
					int id=((Producto) list.getSelectedValue()).getId();
					int cant=Integer.parseInt((String)comboBox.getSelectedItem());
					if(conexionBD.modificarCantidad(id, cant)) {
						JOptionPane.showMessageDialog(null, "Existencias añadidas con éxito");
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "Fallo al añadir las existencias");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Primero elije uno, BRUTO");
				}
			}
		});
		btnNewButton.setBounds(335, 213, 89, 37);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Cantidad");
		lblNewLabel_1.setBounds(339, 45, 73, 14);
		contentPane.add(lblNewLabel_1);
		cargarProductosDesdeBD();

	}
	@SuppressWarnings("unchecked")
	private void cargarProductosDesdeBD() {
	    try {
	    	R	 =conexionBD.getMyRs("productos");
	        while (R.next()) {
	        	if(R.getInt("cantidad")!=0){
	            int id = R.getInt("id");
	            String nombre = R.getString("nombre");
	            double precio = R.getDouble("precio");
	            Producto p=new Producto(id, nombre, precio);
	            modeloLista.addElement(p);
	        	}
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
