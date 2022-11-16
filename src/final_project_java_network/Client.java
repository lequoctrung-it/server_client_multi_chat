package final_project_java_network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

public class Client {
	private InetAddress host;
	private int port;
	
	public Client(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private void execute() throws IOException {
		Socket client = new Socket(host, port);
		// ReadClient & WriteClient start here
	}
}

//class ReadClient extends Thread {
//	private Socket client;
//	private JTextArea messageChatbox;
//
//	public ReadClient(Socket client, JTextArea messageChatbox) {
//		this.client = client;
//		this.messageChatbox = messageChatbox;
//	}
//	
//	@Override
//	public void run() {
//		DataInputStream dataInputStream = null;
//		try {
//			dataInputStream = new DataInputStream(client.getInputStream());
//			while (true) {
//				String sms = dataInputStream.readUTF();
//				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + sms); 
//			}
//		} catch (Exception e) {
//			try {
//				dataInputStream.close();
//				client.close();
//			} catch (IOException ex) {
//				System.out.println("Server disconnected!");
//			}
//		}
//	}
//}
//
//class WriteClient extends Thread {
//	private Socket client;
//	private JTextArea messageChatbox;
//	String messageSend;
//
//	public WriteClient(Socket client, JTextArea messageChatbox, String messageSend) {
//		this.client = client;
//		this.messageChatbox = messageChatbox;
//		this.messageSend = messageSend;
//	}
//	
//	@Override
//	public void run() {
//		DataOutputStream dataOutputStream = null;
//		try {
//			dataOutputStream = new DataOutputStream(client.getOutputStream());
//			while (true) {
//				dataOutputStream.writeUTF(messageSend);
//				dataOutputStream.flush();
//				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + messageSend); 
//			}
//		} catch (Exception e) {
//			try {
//				dataOutputStream.close();
//				client.close();
//			} catch (IOException ex) {
//				System.out.println("Server disconnected!");
//			}
//		}
//	}
//}
