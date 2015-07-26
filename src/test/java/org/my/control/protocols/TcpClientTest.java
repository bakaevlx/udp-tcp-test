package org.my.control.protocols;

import java.net.InetAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.my.control.protocols.tcp.sensor.TcpClient;

/**
 * 
 * 
 * nslookup stackoverflow.com
 * 
 * Name: stackoverflow.com Address: 198.252.206.16
 * 
 * @author Sasha
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TcpClientTest {

	@Test
	public void testConnection() throws Exception {
		TcpClient client = null;
		try {
			// client = new TcpClient("198.252.206.16", 80);
			client = new TcpClient(InetAddress.getByName("stackoverflow.com"),
					80);
			// HTTP requests end with a blank line
			client.run("GET / HTTP/1.1\nHost: stackoverflow.com\n\n");
		} finally {
			if (client != null)
				client.closeSocket();
		}
	}

}