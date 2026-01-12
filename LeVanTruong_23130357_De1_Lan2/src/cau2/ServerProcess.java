package cau2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.classfile.instruction.SwitchCase;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread{
	BufferedReader netIn;
	PrintWriter netOut;
	Socket socket;
	String com, param;
	Dao dao;
	public ServerProcess(Socket socket) {
		this.socket =socket;
		try {
			netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			 dao = new Dao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			netOut.println("WELCOME TO NLU EBANK...");
			netOut.flush();
			String line;
			String res;
			String lastUser=null;
			boolean isLogin = false;
			while(!isLogin) {
				line = netIn.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analys(line);
				switch (com) {
				case "USER": {
					if(dao.checkUser(param)) {
						res="OK";
						lastUser = param;
					}else {
						res ="user ko ton tai";
					}
					break;
				}
				case "PASS": {
					if(lastUser!=null) {
						if(dao.login(lastUser, param)) {
							res = "OK Login";
							isLogin = true;
						}else {
							res = "sai pass";
						}
					}else {
						res ="ten dang nhap con trong";
					}
					break;
				}
				default:
					res="Lenh ko hop le";
				}
				netOut.println(res);
				netOut.flush();
			}
			
			while(isLogin) {
				String numberAccount = dao.getNumberAccount(lastUser);
				line = netIn.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analys(line);
				switch (com) {
				case "GUI": {
					if(dao.deposit(numberAccount, Double.parseDouble(param))) {
						res = "gui thanh con so tien: "+param;
						dao.insertLog(numberAccount, com, Double.parseDouble(param));
					}else {
						res="so du khong du";
					}
					break;
				}
				case "RUT": {
					if(dao.withdraw(numberAccount, Double.parseDouble(param))) {
						res = "rut thanh con so tien: "+param;
						dao.insertLog(numberAccount, com, Double.parseDouble(param));
					}else {
						res="loi giao dich";
					}
					break;
				}
				case "SODU": {
					double balance = dao.getBalance(numberAccount);
					res = "So du hien tai: "+ balance;
					break;
				}
				case "NHATKY": {
					res="lich su giao dich: "+ dao.getLog(numberAccount);
					break;
				}
				default:
					res="lenh ko hop le";
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
	private void analys(String line) {
		StringTokenizer st = new StringTokenizer(line, "\t");
		com = st.nextToken().toUpperCase();
		param =  line.substring(com.length()).trim();
	}
}
