package cau2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.StringTokenizer;

public class Client {
	BufferedReader userIn;
	ISearch server;
	String com, param;
	SearchImpl dao;

	public Client() {
		try {
			userIn = new BufferedReader(new InputStreamReader(System.in));
			Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1090);
			server = (ISearch) reg.lookup("Search");
			dao = new SearchImpl();
		} catch (Exception e) {

		}
	}

	public void run() {
		try {
			System.out.println("Welcome");
			String line;
			String lastUser=null,res =null;
			boolean iLogin = false;
			while (!iLogin) {
				line = userIn.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analys(line);
				switch (com) {
				case "USER": {
					if(dao.checkUser(param)) {
						lastUser = param;
						res = "OK";
					}else {
						res ="User khong ton tai";
					}
					break;
				}
				case "PASS": {
					if(lastUser!=null && dao.login(lastUser, param)) {
						iLogin=true;
						res = "Login";
					}else {
						res ="sai pass";
					}
					break;
				}
				default:
					res = "lenh ko hop le";
				}
				System.out.println(res);
			}
			while (iLogin) {
				String numberAccount = dao.getNumberAccount(lastUser);
				line = userIn.readLine();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				analys(line);
				switch (com) {
				case "DEPOSIT":
					if(dao.deposit(numberAccount, Double.parseDouble(param))) {
						System.out.println("Dang chay...");
					    Thread.sleep(3000);
						res = "Rut thanh cong so tien" + param;
						dao.insertLog(numberAccount, com, Double.parseDouble(param));
					}else {
						res = "So du khong du";
					}
					break;
				case "WITHDRAW":
					if(dao.withdraw(numberAccount, Double.parseDouble(param))) {
						res = "Gui thanh cong so tien" + param;
						dao.insertLog(numberAccount, com, Double.parseDouble(param));
					}else {
						res = "sai stk";
					}
					break;
				case "BALANCE":
					if(dao.getBalance(numberAccount)!=-1) {
						res = "So du: " + dao.getBalance(numberAccount);
					}else {
						res = "Sai so tai khaon";
					}
					break;
				case "LOG":
					if(dao.getLog(numberAccount).size()>0) {
						res = "SoTaiKhoan\tngay\tThaotac\tsotien\n"+dao.getLog(numberAccount);
					}else {
						res="chua co giao dich nao";
					}
					break;

				default:
					res = "sai cu phap";
					break;
				}
				System.out.println(res);
			}
			System.out.println("BYE");
			userIn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void analys(String line) {
		StringTokenizer st = new StringTokenizer(line,"\t");
		com = st.nextToken().toUpperCase();
		param = line.substring(com.length()).trim();
		
	}
	public static void main(String[] args) {
		new Client().run();
	}
}
