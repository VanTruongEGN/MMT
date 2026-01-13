package server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	BufferedOutputStream bos;
	String com, param;
	Dao dao;

	public ServerProcess(Socket socket) {
		try {
			this.socket = socket;
			netIn = new DataInputStream(socket.getInputStream());
			netOut = new DataOutputStream(socket.getOutputStream());
			dao = new Dao();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void run() {
		try {
			netOut.writeUTF("Welcome to bank");
			netOut.flush();
			String line;
			String res = null;
			String lastUser = null;
			boolean isLogin = false;
			while (!isLogin) {
				line = netIn.readUTF();
				if (line.equalsIgnoreCase("QUIT")) {
					break;
				}
				analyze(line);
				switch (com) {
				case "USER": {
					System.out.println(com);
					System.out.println(param);
					System.out.println(dao.checkUser(param));
					if (dao.checkUser(param)) {
						res = "OK";
						lastUser = param;
						System.out.println("Ok");
					} else {
						res = "User ko ton tai";
					}
					break;
				}
				case "PASS": {
					if (lastUser != null && dao.login(lastUser, param)) {
						res = "Ok login";
						isLogin = true;
					}else {
						res="sai pass";
					}
					break;
				}
				default:
					res = "Lenh ko hop le";
				}
				System.out.println(res);
				netOut.writeUTF(res);
				netOut.flush();

			}
//			while (isLogin) {
//				
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void analyze(String command) {
	    StringTokenizer st = new StringTokenizer(command, "|");
	    com = st.nextToken().toUpperCase();
	    param = st.hasMoreTokens() ? st.nextToken().trim() : "";
	}
}
