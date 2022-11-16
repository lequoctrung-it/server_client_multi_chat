package final_project_java_network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Server {
	private int port;
	public static ArrayList<Socket> listSocket;
	
	public Server(int port) {
		this.port = port;
	}
	
	private void execute() throws IOException {
		ServerSocket server = new ServerSocket(port);
		// start writeserver here
		WriteServer writeServer = new WriteServer();
		writeServer.start();
		while (true) {
			Socket socket = server.accept();
			Server.listSocket.add(socket);
			//start readserver here
			ReadServer readServer = new ReadServer(socket);
			readServer.start();
		}
	}
}

//class ReadServer extends Thread {
//	private Socket server;
//	private JTextArea messageChatbox;
//	
//	public ReadServer(Socket server) {
//		this.server = server;
//		this.messageChatbox = messageChatbox;  
//	}
//	
//	@Override
//	public void run() {
//		DataInputStream dataInputStream = null;
//		try {
//			dataInputStream = new DataInputStream(server.getInputStream());
//			while (true) {
//				String sms = dataInputStream.readUTF();
//				for (Socket item : Server.listSocket) {
//					if (item.getPort() != server.getPort()) {
//						DataOutputStream dataOutputStream = new DataOutputStream(item.getOutputStream());
//						dataOutputStream.writeUTF(sms);
//					}
//				}
//				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + sms);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}

//class WriteServer extends Thread {
//	private JTextArea messageChatbox;
//	String messageSend;
//	
//	public WriteServer(JTextArea messageChatbox, String messageSend) {
//		this.messageChatbox = messageChatbox;
//		this.messageSend = messageSend;
//	}
//
//	@Override
//	public void run() {
//		DataOutputStream dataOutputStream = null;
//		while (true) {
//			try {
//				for (Socket item : Server.listSocket) {
//					dataOutputStream = new DataOutputStream(item.getOutputStream());
//					dataOutputStream.writeUTF(messageSend);
//				}
//				messageChatbox.setText(messageChatbox.getText().trim() + "\n" + messageSend);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
