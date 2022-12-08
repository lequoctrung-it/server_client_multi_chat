package udp_chat;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Server_GUI extends JFrame {
	private JPanel contentPane;
	private static int port;
	private InetAddress clientIP;
	private int clientPort;
	public static Map<DatagramPacket, Integer> listSK;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_GUI frame = new Server_GUI();
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
	
	public Server_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Start Sever");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Server.listSK = new HashMap<DatagramPacket, Integer>();
				Server server = new Server(15797);
				try {
					server.start();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
		});
		btnNewButton_1.setBounds(101, 99, 239, 67);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("UDP Server Chat Multiclient App");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(86, 42, 264, 32);
		contentPane.add(lblNewLabel);
		
		
	}
}

