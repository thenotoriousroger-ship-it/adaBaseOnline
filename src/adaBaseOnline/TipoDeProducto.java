package adaBaseOnline;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TipoDeProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Ventas ventanaVentas;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TipoDeProducto frame = new TipoDeProducto(null);
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
	public TipoDeProducto(Ventas ventas) {
	    this.ventanaVentas = ventas;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Pastel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pastel nuevaVentana = new Pastel(ventanaVentas);
                nuevaVentana.setVisible(true);
                dispose();
			}
		});
		btnNewButton.setBounds(10, 47, 141, 124);
		contentPane.add(btnNewButton);
		
		JButton btnPostre = new JButton("Postre");
		btnPostre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Postre nuevaVentana = new Postre(ventanaVentas);
                nuevaVentana.setVisible(true);
                dispose();
			}
		});
		btnPostre.setBounds(161, 47, 141, 124);
		contentPane.add(btnPostre);
		
		JLabel lblNewLabel = new JLabel("Tipo de producto");
		lblNewLabel.setBounds(114, 22, 81, 14);
		contentPane.add(lblNewLabel);

	}

}
