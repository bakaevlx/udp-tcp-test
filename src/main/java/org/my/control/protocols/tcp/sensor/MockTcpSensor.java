package org.my.control.protocols.tcp.sensor;

import java.net.*;
import java.io.*;

import static org.my.control.protocols.common.UdpTcpUtil.*;

/**
 * The server socket stays open and can process multiple (sequential) client
 * connections.
 */
public class MockTcpSensor {
	private ServerSocket serverSocket;
	private static int counter = 0;
	private boolean exitFlag = false;

	public MockTcpSensor() throws IOException {
		serverSocket = new ServerSocket(SENSOR_PORT);
		System.out.println("ServerSocket created");
	}

	public void run() throws Exception {

		// could be made multi-threaded 
		while (!exitFlag) {
			processCleintConnection();
		}

	}

	private void processCleintConnection() throws IOException {

		Socket clientSocket = null;

		try {
			System.out.println("Waiting for connection.....");
			clientSocket = serverSocket.accept();
			System.out.println("Connection successful");

			try (PrintWriter out = new PrintWriter(
					clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));) {

				String s; // EOL means new new command/end of transfer here
				while ((s = in.readLine()) != null) {

					System.out.println("Request=" + s);

					if (isExitCommand(s)) {
						exitFlag = true;
					}

					s = createResponseMsg();
					System.out.print("Send=" + s);
					out.println(s);
				}

			}
		} finally {
			if (clientSocket != null) {
				clientSocket.close();
				System.out.println("Client socket closed");
			}
		}
	}

	private String createResponseMsg() {
		return "" + 120 + counter++;
	}

	private void closeServerSocket() throws IOException {
		serverSocket.close();
		System.out.println("Server socket closed");
	}

	public static void main(String[] args) throws Exception {
		MockTcpSensor sens = null;
		try {
			sens = new MockTcpSensor();
			sens.run();
		} finally {
			if (sens != null)
				sens.closeServerSocket();
		}
	}

}
