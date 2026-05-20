package adaBaseOnline;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ClienteOVendedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Conecta conexionBD = new Conecta();
	ResultSet R;
	private JTextField textUsr;
	private JPasswordField passwordField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteOVendedor frame = new ClienteOVendedor();
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
	
	public ClienteOVendedor() {
		
		R	 =conexionBD.getMyRs("vendedor");
								
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 228));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido a Danny Desserts and More");
		lblNewLabel.setForeground(new Color(223, 90, 119));
		lblNewLabel.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel.setBounds(74, 23, 273, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnSoyVendedor = new JButton("Iniciar sesión");
		btnSoyVendedor.setForeground(new Color(223, 90, 119));
		btnSoyVendedor.setBackground(new Color(243, 148, 172));
		btnSoyVendedor.setFont(new Font("Bahnschrift", Font.BOLD, 11));
		btnSoyVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario=textUsr.getText();
				String contraseña=new String(passwordField.getPassword());
				try {
					boolean usuarioEncontrado = false;
					while (R.next()) {
		                if (usuario.equalsIgnoreCase(R.getString("nombre")) && contraseña.equals(R.getString("contraseña"))) {
		                    usuarioEncontrado = true;
		                    break;
		                }
		            }
					if (usuarioEncontrado) {
		                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
		                Ventas nuevaVentana = new Ventas(usuario);;
		                nuevaVentana.setVisible(true);
		                dispose();
		            } else {
		                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
		            }
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnSoyVendedor.setBounds(262, 204, 150, 46);
		contentPane.add(btnSoyVendedor);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Emmanuel Soto Sosa\\Pictures\\459383318_484015391214272_5079545940615537764_n.jpg"));
		lblNewLabel_1.setBounds(189, 45, 48, 46);
		contentPane.add(lblNewLabel_1);
		
		textUsr = new JTextField();
		textUsr.setBounds(87, 102, 325, 20);
		contentPane.add(textUsr);
		textUsr.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(87, 133, 325, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Usuario");
		lblNewLabel_2.setForeground(new Color(223, 90, 119));
		lblNewLabel_2.setFont(new Font("Bahnschrift", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 105, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Contraseña");
		lblNewLabel_3.setForeground(new Color(223, 90, 119));
		lblNewLabel_3.setFont(new Font("Bahnschrift", Font.BOLD, 11));
		lblNewLabel_3.setBounds(10, 136, 67, 14);
		contentPane.add(lblNewLabel_3);

	}
}
