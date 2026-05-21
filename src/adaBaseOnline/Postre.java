package adaBaseOnline;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adaBaseOnline.Conecta.Producto;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Postre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	DefaultListModel modeloLista = new DefaultListModel<>();
	private Conecta conexionBD = new Conecta();
	ResultSet R;
	private Ventas ventanaVentas;

	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Postre frame = new Postre(null);
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
	public Postre(Ventas ventas) {
	    this.ventanaVentas = ventas;

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 210, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Postres");
		lblNewLabel.setForeground(new Color(255, 128, 128));
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list = new JList(modeloLista);
		list.setBackground(new Color(255, 255, 255));
		list.setBounds(10, 36, 319, 214);
		contentPane.add(list);
		cargarProductosDesdeBD();
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.setBackground(new Color(255, 128, 128));
		btnNewButton.setForeground(new Color(255, 128, 128));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=((Producto) list.getSelectedValue()).getId();
				ventanaVentas.recibirProductoSeleccionado(id, false);
				dispose();
			}
		});
		btnNewButton.setBounds(339, 213, 89, 37);
		contentPane.add(btnNewButton);

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
