package org.my.control.protocols.tcp.sensor;

import static org.my.control.protocols.common.UdpTcpUtil.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	private Socket socket = null;

	public TcpClient(String ip, int port) throws UnknownHostException,
			IOException {
		socket = new Socket(ip, port);
		System.out.println("Socket created IP=" + ip + " port=" + port);
	}

	/**
	 * To use a host name.
	 */
	public TcpClient(InetAddress addr, int port) throws UnknownHostException,
			IOException {
		socket = new Socket(addr, port);
		System.out.println("Socket created host=" + addr.getHostName()
				+ " port=" + port);
	}

	public void run(String msg) throws Exception {

		// EOL means new command
		try (PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {

			System.out.println("Streams opened");

			System.out.println("Send=" + msg);

			out.println(msg);
			out.flush();

			String s = in.readLine();
			System.out.println("Received=" + s);

		}
	}

	public void closeSocket() throws IOException {
		socket.close();
		System.out.println("Socket closed");
	}

	public static void main(String[] args) throws Exception {

		String ip = SENSOR_IP;
		int port = SENSOR_PORT;
		String msg = "test" + System.currentTimeMillis();

		TcpClient client = null;
		try {
			client = new TcpClient(ip, port);
			client.run(msg);
		} finally {
			if (client != null)
				client.closeSocket();
		}

	}

}
