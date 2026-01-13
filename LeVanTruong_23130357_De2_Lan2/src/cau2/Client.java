package cau2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.StringTokenizer;

public class Client {
	BufferedReader userIN;
	ISearch server;
	String com, param;
	SearchImpl dao;
	public Client() {
		try {
			userIN = new BufferedReader(new InputStreamReader(System.in));
			Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
			server = (ISearch) reg.lookup("Search");
			dao = new SearchImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			System.out.println("WELCOME TO MANAGEMENT BANK...");
			String line;
			String res = null;
			String lastUser = null;
			boolean isLogin = false;
			while(!isLogin) {
				line = userIN.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analyze(line);
				switch (com) {
				case "USER": {
					if(dao.checkUser(param)) {
						lastUser = param;
						res = "OK";
					}else {
						res="user ko ton tai";
					}
					break;
				}
				case "PASS": {
					if(lastUser!=null) {
						if(dao.login(lastUser, param)) {
							res="Ok login";
							isLogin = true;
						}else {
							res="sai mk";
						}
					}else {
						res  ="ko tim thay user";
					}
					break;
				}
				default:
					res = "lenh ko hop le";
				}
				System.out.println(res);
			}
			while(isLogin) {
				String numberAccount = dao.getNumberAccount(lastUser);
				line = userIN.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analyze(line);
				switch (com) {
				case "DEPOSIT": {
					if(dao.deposit(numberAccount, Double.parseDouble(param))) {
						res ="gui thanh cong so tien " + param;
						dao.insertReport(numberAccount, com, Double.parseDouble(param));
					}else {
						res="loi giao dich";
					}
					break;
				}
				case "WITHDRAW": {
					if(dao.withdraw(numberAccount, Double.parseDouble(param))) {
						res ="rut thanh cong so tien " + param;
						dao.insertReport(numberAccount, com, Double.parseDouble(param));

					}else {
						res="so du ko du";
					}
					break;
				}
				case "BALANCE": {
					 	double balance = dao.getBalance(numberAccount);
					 	if(balance!=-1) {
					 		res="so du: "+ balance;
					 	}
					break;
				}
				case "REPORT": {
					if(dao.getReport(numberAccount).size()>0) {
						res = "lich su giao dich" + dao.getReport(numberAccount);
					}else {
						res = "ko co giao dich nao";
					}
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + com);
				}
				
				System.out.println(res);
			}
			System.out.println("bye");
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	private void analyze(String line) {
		StringTokenizer st = new StringTokenizer(line, "\t");
		com = st.nextToken().toUpperCase();
		param = line.substring(com.length()).trim();
		
	}
	public static void main(String[] args) {
		new Client().run();
	}
}
