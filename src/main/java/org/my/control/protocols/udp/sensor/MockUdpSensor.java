package org.my.control.protocols.udp.sensor;

import static org.my.control.protocols.common.UdpTcpUtil.*;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * UDP unicast. Receives a command string and sends a reply (for example voltage
 * reading) to the requester IP.
 * 
 */
public class MockUdpSensor extends AbstractUdpApp implements Callable<String> {
	private boolean exitFlag = false;

	public MockUdpSensor() throws SocketException {

		int port = SENSOR_PORT;
		
		socket = new DatagramSocket(null);
		socket.bind(new InetSocketAddress(port));

		System.out.println("Waiting for UDP packet on port " + port);
	}

	public String call() throws Exception {

		while (!exitFlag) {

			String s = receiveDatagram();
			
			if (isExitCommand(s))
				exitFlag = true;

			Thread.sleep(500);

			// reply: for example voltage reading
			String payload = "" + (100 + counter);
			sendDatagram(CLIENT_IP, CLIENT_UDP_PORT, payload.getBytes());
		}

		return "MockUdpSensor exit; counter=" + counter;
	}

	public static void main(String[] args) throws Exception {

		Callable<String> sens = null;

		try {
			
			ExecutorService executor = Executors
					.newSingleThreadScheduledExecutor();
			sens = new MockUdpSensor();
			Future<String> future = executor.submit(sens);

			while (!future.isDone()) {
				Thread.sleep(5000);
			}

			System.out.println(future.get());
			executor.shutdown();
			
		} finally {
			if (sens != null)
				((MockUdpSensor) sens).closeSocket();
		}
	}

}
