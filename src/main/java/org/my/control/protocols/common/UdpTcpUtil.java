package org.my.control.protocols.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UdpTcpUtil {
	public static final int MAX_LEN = 1024;
	// Sensor, Arduino or Mock
	public static final int SENSOR_PORT = 8888;
	public static final String SENSOR_IP
		= "169.254.120.176";
//		= "127.0.0.1";	// Mock sensor
	// for UDP tests only;
	// Laptop
	public static final String CLIENT_IP
		= "169.254.119.154";
	public static final int CLIENT_UDP_PORT = 9990;

	private UdpTcpUtil() {

	}

	public static boolean isExitCommand(String s) {
		boolean res = s.toLowerCase().indexOf("bye") != -1 ? true : false;
		System.out.println("isExitCommand s=" + s + " res=" + res);
		return res;
	}

	public static String getUserInput() throws IOException {
		try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));) {
			String s;
			while (true) {
				System.out.println("Input: ");
				s = stdIn.readLine().trim();
				if (s != null && s.length() > 0)
					break;
			}
			return s;
		}
	}

	public static String ipArrToString(byte[] ip) {
		StringBuilder res = new StringBuilder();
		for (byte b : ip) {
			res.append(b & 0xFF);
			res.append('.');
		}
		return res.toString().substring(0, res.length() - 1);
	}

}
