package quanlisinhvien;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ServerProcess extends Thread {
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;
	String com, param;
	Dao dao;

	public ServerProcess(Socket socket) {
		super();
		this.socket = socket;
		try {
			netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			dao = new Dao();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void run() {
		try {
			netOut.println("System is ready");
			netOut.flush();
			String line;
			String res;
			boolean isLogin = false;
			String lastUser = null;

			while (true) {
				line = netIn.readLine();
				if (line == null)
					break;
				analyze(line);
				if (com.equalsIgnoreCase("EXIT")) {
					break;
				}

				if (!isLogin) {
					switch (com) {
					case "USER":
						if (dao.checkUser(param)) {
							res = "+OK";
							lastUser = param;
						} else {
							res = "User not found";
							lastUser = null;
						}
						break;

					case "PASS":
						if (lastUser == null) {
							res = "-Error. Invalid user name";
						} else if (dao.checkPass(lastUser, param)) {
							isLogin = true;
							res = "+OK. Login";
						} else {
							res = "-Error. Login";
							lastUser = null;
						}
						break;

					default:
						res = "Please login first";
						break;
					}
				} else {
					switch (com) {
					case "FID":
						Student st = dao.findById(Integer.parseInt(param));
						if (st != null) {
							res = "result: " + st;
						} else {
							res = "not found";
						}
						break;

					case "FBN":
						List<Student> list = dao.findByName(param);
						if (!list.isEmpty()) {
							res = "result: " + list;
						} else {
							res = "not found " + param;
						}
						break;

					default:
						res = "Command is invalid";
						break;
					}
				}
				netOut.println(res);
				netOut.flush();
			}

			netOut.println("Bye");
			netOut.flush();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyze(String line) {
		StringTokenizer st = new StringTokenizer(line);
		com = st.nextToken().toUpperCase();
		param = line.substring(com.length()).trim();
	}

}
