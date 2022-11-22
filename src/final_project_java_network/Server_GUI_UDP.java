package final_project_java_network;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Server_GUI_UDP extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static int port = 15797;
	private static InetAddress clientIP;
	private static int clientPort;
	static Map<DatagramPacket, Integer> listSK;
	

//	
//	private void execute()throws IOException{
//		DatagramSocket server = new DatagramSocket(port);
//		WriteServerUDP write = new WriteServerUDP(server);
//		write.start();
//		
//		while(true) {
//			String sms = recieveData(server);
//			for(DatagramPacket item: listSK.keySet()) {
//				sendData(sms, server, item.getAddress(), item.getPort());
//			}
//			System.out.println(sms);
//		}	
//	}
	private static String recieveData( DatagramSocket server) throws IOException{
		byte[] temp  = new byte[1024];
		DatagramPacket recieve_Packet = new DatagramPacket(temp, temp.length);
		server.receive(recieve_Packet);
		clientIP = recieve_Packet.getAddress();
		clientPort = recieve_Packet.getPort();	
		
		checkDuplicate(recieve_Packet);
		
		return new String(recieve_Packet.getData()).trim();
	}
	
	private static void checkDuplicate(DatagramPacket packet) {
		for( DatagramPacket item: listSK.keySet()) {
			if(item.getAddress().equals(packet.getAddress())&& item.getPort()== packet.getPort()) {
				listSK.replace(item, 0);
				return;
			}
		}listSK.put(packet, 0);
	}
	private static void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort) throws IOException {
		byte[] temp = new byte[1024];
		temp = value.getBytes();
		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length,clientIP, clientPort);
		server.send(send_result_Packet);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_GUI_UDP frame = new Server_GUI_UDP();
					frame.setVisible(true);
//					Server_GUI_UDP.listSK = new HashMap<DatagramPacket, Integer>();
//					Server_GUI_UDP server = new Server_GUI_UDP(15797);
//					server.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Server_GUI_UDP.listSK = new HashMap<DatagramPacket, Integer>();
			DatagramSocket server = new DatagramSocket(port);
			WriteServerUDP write = new WriteServerUDP(server);
			write.start();
			
			while(true) {
				String sms = recieveData(server);
				for(DatagramPacket item: listSK.keySet()) {
					sendData(sms, server, item.getAddress(), item.getPort());
				}
				System.out.println(sms);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 */
	public Server_GUI_UDP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(194, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 34, 414, 162);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(10, 207, 300, 43);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(320, 207, 104, 43);
		contentPane.add(btnNewButton);
	}

}
class WriteServerUDP extends Thread{
	private DatagramSocket server;

	public WriteServerUDP(DatagramSocket server) {
		super();
		this.server = server;
	}
	
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			String sms = sc.nextLine();
			try {
				for(DatagramPacket item: Server_GUI_UDP.listSK.keySet()) {
					sendData(sms, server, item.getAddress(), item.getPort());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private void sendData(String value, DatagramSocket server, InetAddress clientIP, int clientPort) throws IOException {
		byte[] temp = new byte[1024];
		temp = value.getBytes();
		DatagramPacket send_result_Packet = new DatagramPacket(temp, temp.length,clientIP, clientPort);
		server.send(send_result_Packet);
	}
}
