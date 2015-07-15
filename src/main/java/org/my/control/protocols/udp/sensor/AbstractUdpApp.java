package org.my.control.protocols.udp.sensor;

import static org.my.control.protocols.common.UdpTcpUtil.MAX_LEN;

import java.net.*;
import java.io.*;

/**
 * TODO can the socket stay open? Or open anew one every time?
 */
public class AbstractUdpApp {
	protected DatagramSocket socket;
	protected int counter = 0;

	protected String receiveDatagram() throws Exception {

		byte[] buf = new byte[MAX_LEN];
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		socket.receive(p);

		String payload = new String(p.getData(), 0, p.getLength());
		InetAddress senderAddress = p.getAddress();
		int senderPort = p.getPort();

		System.out.println("UDP packet received: senderAddress="
				+ senderAddress.getHostAddress() + ":" + senderPort + " data="
				+ payload);
		return payload;
	}

	protected void sendDatagram(String ip, int port, byte[] payload)
			throws Exception {

		InetAddress addr = InetAddress
		// .getByAddress(ip);
				.getByName(ip);

		DatagramPacket packet = new DatagramPacket(payload, payload.length,
				addr, port);
		socket.send(packet);

		System.out.println("UDP packet sent to " + ip + ":" + port
				+ " payload=" + new String(payload));

	}

	protected void closeSocket() throws IOException {
		socket.close();
		System.out.println("Socket closed");
	}

}
