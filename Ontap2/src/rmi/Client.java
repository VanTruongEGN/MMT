package rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
	BufferedReader userIn;
	ISearch server;
	String com, param;
	Dao dao;
	public Client() throws AccessException, RemoteException, NotBoundException, ClassNotFoundException, SQLException {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		Registry reg = LocateRegistry.getRegistry("127.0.0.1", 12345);
		server = (ISearch) reg.lookup("SEARCH");
		dao = new Dao();
	}
	public void run() {
		try {
			System.out.println("System is ready");
			String line, res = null, lastUser = null;
			boolean isLogin=false;
			while (!isLogin) {
				line = userIn.readLine();
				if(line.equalsIgnoreCase("Exit")) {
					break;
				}
				analyze(line);
				switch (com) {
				case "USER": {
					if(dao.checkUser(param)) {
						lastUser = param;
						res ="+OK";
					}else {
						res = "user not found";
					}
					break;
				
				}
				case "PASS":{
					if(lastUser==null) {
						res="user not found";
					}else {
						if(dao.login(lastUser, param)) {
							res="OK login";
							isLogin=true;
						}else {
							res="login fall";
						}
					}
					break;
				}
				default:
					res = "Command invalid";
				}
				System.out.println(res);
			}
			
			List<Student> list = new ArrayList<Student>();
			while (isLogin) {
				line = userIn.readLine();
				if(line.equalsIgnoreCase("Exit")) {
					break;
				}
				analyze(line);
				switch (com) {
				case "FID": {
					Student  st =null;
					if(dao.findById(Integer.parseInt(param))!=null) {
						st = dao.findById(Integer.parseInt(param));
						res ="result: " + st; 
					}else {
						res ="not found";
					}
					break;
				
				}
				case "FBN":{
					list.addAll(dao.findByName(param));
					if(list.size()>0) {
						res = "result:\n\t" + list;
					}else {
						res = "not found";
					}
					break;
				}
				default:
					res = "Command invalid";
				}
				System.out.println(res);
			}
			userIn.close();
			System.out.println("Bye");
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	private void analyze(String line) {
		StringTokenizer st = new StringTokenizer(line);
		com = st.nextToken().toUpperCase();
		param = line.substring(com.length()).trim();
	}
	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException, ClassNotFoundException, SQLException {
		new Client().run();
	}
}	
