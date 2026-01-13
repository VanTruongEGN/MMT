package tcpproduct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ServerProcess extends Thread{
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;
	String com;
	List<String> param;
	Dao dao;
	public ServerProcess(Socket socket) {
		try {
			this.socket = socket;
			netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			dao = Dao.getInstance();
			param = new ArrayList<String>();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void run() {
		try {
			netOut.println("WELCOME TO PRODUCT MANAGEMENT SYSTEM");
			netOut.flush();
			String line;
			String res = null;
			while(true) {
				line = netIn.readLine();
				if(line.equalsIgnoreCase("QUIT")) {
					break;
				}
				analyze(line);
				System.out.println(com);
				switch (com) {
				case "ADD": {
					if(param.size()==4) {
						int id = Integer.parseInt(param.get(0));
						String name = param.get(1);
						int count = Integer.parseInt(param.get(2));
						double price = Double.parseDouble(param.get(3));
						Product p = new Product(id,name ,count ,price);
						if(dao.add(p)) {
							res = "OK";
						}else {
							res ="sp da ton tai";
						}
					}
					break;
				}
				case "BUY":{
					String fail= "Ko the mua sp: ";
					boolean flag=false;
					for (String id : param) {
						if(!dao.buy(Integer.parseInt(id))) {
							fail += id+",";
							flag = true;
						}
					}
					if(flag) {
						res = fail;
					}else {
						res ="mua thanh cong";
					}
					break;
				}
				case "PRICE":{
					double from = Double.parseDouble(param.get(0));
					double to = Double.parseDouble(param.get(1));
					if(dao.price(from, to).size()>0) {
						res = "ket qua "+ dao.price(from, to);
					}else {
						res ="ko tim thay";
					}
					
					break;
				}
				case "NAME":{
					
					if(dao.name(param.get(0)).size()>0) {
						res = "ket qua "+ dao.name(param.get(0));
					}else {
						res ="ko tim thay";
					}
					
					break;
				}
				default:
					res ="lenh ko hop le";
				}
				netOut.println(res);
				netOut.flush();
			}
			netOut.println("BYE");
			netOut.flush();
			socket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void analyze(String line) {
		param.clear();
		StringTokenizer st = new StringTokenizer(line, "\t");
		com = st.nextToken().toUpperCase();
		while(st.hasMoreTokens()) {
			param.add(st.nextToken());
		}
	}
}
