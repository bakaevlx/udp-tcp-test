package org.my.control.protocols.udp.sensor;

import static org.my.control.protocols.common.UdpTcpUtil.*;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * To test an Adruino sensor, or use MockTcpSensor.
 * 
 * @author Sasha
 *
 */
public class UdpClient extends AbstractUdpApp {
	
	public UdpClient(int port) throws SocketException {

		socket = new DatagramSocket(null);
		socket.bind(new InetSocketAddress(port));

		System.out.println("Opened client DatagramSocket on port " + port);
	}

	public static void main(String[] args) throws Exception {

		UdpClient client = null;
		int port = CLIENT_UDP_PORT;
		
		try {
			client = new UdpClient(port);

			String payload = "test1";
//				= getUserInput();
			client.sendDatagram(SENSOR_IP, SENSOR_PORT, payload.getBytes());

			client.receiveDatagram();

		} finally {
			client.closeSocket();
		}
	}

}
