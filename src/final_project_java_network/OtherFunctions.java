package final_project_java_network;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class OtherFunctions extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField numberA;
	private static JTextArea resultArea;

	// Chat field
	private static int port = 3001;
	static DataInputStream dataInputStream;
	static Socket client;
	private JTextField numberB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OtherFunctions frame = new OtherFunctions();
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
	public OtherFunctions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel nameLabel = new JLabel("Multi-function application");
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(12, 12, 509, 15);
		contentPane.add(nameLabel);

		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(526, 8, 139, 25);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client = new Socket(InetAddress.getLocalHost(), port);
					Thread t = new Thread(OtherFunctions.this);
					t.start();

				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton);

		JLabel lblNumberA = new JLabel("Number a:");
		lblNumberA.setBounds(134, 63, 80, 15);
		contentPane.add(lblNumberA);

		numberA = new JTextField();
		numberA.setToolTipText("");
		numberA.setBounds(221, 47, 94, 48);
		contentPane.add(numberA);
		numberA.setColumns(10);

		JLabel lblNumberB = new JLabel("Number b:");
		lblNumberB.setBounds(355, 63, 80, 15);
		contentPane.add(lblNumberB);

		numberB = new JTextField();
		numberB.setToolTipText("");
		numberB.setColumns(10);
		numberB.setBounds(442, 47, 94, 48);
		contentPane.add(numberB);

		JButton btnSquareArea = new JButton("Square area");
		btnSquareArea.setBounds(26, 107, 172, 25);
		btnSquareArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.SQUARE_AREA.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnSquareArea);

		JButton btnSquare = new JButton("Square perimeter");
		btnSquare.setBounds(26, 144, 172, 25);
		btnSquare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.SQUARE_PERIMETER.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnSquare);

		JButton btnRectangleArea = new JButton("Rectangle area");
		btnRectangleArea.setBounds(214, 107, 193, 25);
		btnRectangleArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.RECTANGLE_AREA.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnRectangleArea);

		JButton btnRectanglePerimeter = new JButton("Rectangle perimeter");
		btnRectanglePerimeter.setBounds(214, 144, 193, 25);
		btnRectanglePerimeter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.RECTANGLE_PERIMETER.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnRectanglePerimeter);

		JButton btnMinus = new JButton("Minus");
		btnMinus.setBounds(548, 107, 94, 25);
		btnMinus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.MINUS.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnMinus);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(442, 107, 94, 25);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.ADD.name();
				new WriteData(client, sms).start();
			}
		});
		contentPane.add(btnAdd);

		JButton btnDivide = new JButton("Divide");
		btnDivide.setBounds(548, 144, 94, 25);
		btnDivide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.DIVIDE.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnDivide);

		JButton btnMultiply = new JButton("Multiply");
		btnMultiply.setBounds(442, 144, 94, 25);
		btnMultiply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String numA = numberA.getText().trim();
				String numB = numberB.getText().trim();
				String sms = numA + "@" + numB + "@" + Action.MULTIPLY.name();
				new WriteData(client, sms).start();

			}
		});
		contentPane.add(btnMultiply);

		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(46, 237, 70, 15);
		contentPane.add(lblResult);

		resultArea = new JTextArea();
		resultArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		resultArea.setEditable(false);
		resultArea.setBounds(117, 12, 370, 149);
		contentPane.add(resultArea);
		JScrollPane scrollPane = new JScrollPane(resultArea);

		scrollPane.setBounds(123, 207, 519, 87);
		contentPane.add(scrollPane);
	}

	@Override
	public void run() {
		String str="";
		try {
			dataInputStream = new DataInputStream(client.getInputStream());
			while(!str.equals("exit")) {
				str=dataInputStream.readUTF();
				resultArea.setText(resultArea.getText() + "\n" + str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WriteData extends Thread {
	private Socket client;
	String sms;

	public WriteData(Socket client, String sms) {
		this.client = client;
		this.sms = sms;
	}

	@Override
	public void run() {
		DataOutputStream dataOutputStream = null;
		try {
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			dataOutputStream.writeUTF(sms);
			dataOutputStream.flush();
		} catch (Exception e) {
			try {
				dataOutputStream.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Server disconnected!W" + e);
			}
		}
	}
}
