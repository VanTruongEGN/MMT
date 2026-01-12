package cau2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	String com, param;
	List<String> listParam;
	Dao dao;
	public ServerProcess(Socket socket) {
		this.socket = socket;
		try {
			netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			listParam = new ArrayList<String>();
			dao = new Dao();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public void run() {
		try {
			netOut.println("Welcome to ProductManagement System...");
			netOut.flush();
			String line;
			String res="";
			while(true) {
				line = netIn.readLine();
				if(line.equalsIgnoreCase("QUIT")) {
					break;
				}
				analys(line);
				System.out.println(com);
				switch (com) {
				case "ADD": {
					if(listParam.size()==4) {
						Product p = new Product();
						p.setProductId(Integer.parseInt(listParam.get(0)));
						p.setName(listParam.get(1));
						p.setCount(Integer.parseInt(listParam.get(2)));
						p.setPrice(Double.parseDouble(listParam.get(3)));
						if(dao.add(p)) {
							res = "OK";
						}else {
							res = "san pham da ton tai";
						}
					}else {
						res="Ban phai nhap day du 4 field theo thu tu id name count price";
					}
					break;
				}
				case "BUY": {
					String error = "Khong the mua sp co id: ";
					for (String id : listParam) {
						if(dao.buy(Integer.parseInt(id))) {
							res = "OK";
						}else {
							res+="fail";
							error+= id+" ";
						}
					}
					if(res.contains("fail")) {
						res=error;
					}
					break;
				}
				case "PRICE": {
					if(listParam.size()==2) {
						double from = Double.parseDouble(listParam.get(0));
						double to = Double.parseDouble(listParam.get(1));
						if(dao.price(from, to).size()>0) {
							res = "ket qua: \n" + dao.price(from, to);
						}else {
							res = "khong tim thay ket qua phu hop";
						}
					}else {
						res="vui long nhap theo cu phap from....to....";
					}
					break;
				}
				case "NAME": {
					if(listParam.size()==1) {
						String nameP = listParam.get(0);
						if(dao.name(nameP).size()>0) {
							res = "ket qua: \n" + dao.name(nameP);
						}else {
							res = "khong tim thay ket qua phu hop";
						}
					}else {
						res="vui long nhap ten san pham muon tim";
					}
					break;
				}
				
				default:
					res="lenh khong hop le";
				}
				netOut.println(res);
				netOut.flush();
				
			}
			netOut.println("Bye");
			netOut.flush();
			socket.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public void analys(String line) {
		listParam.clear();
		StringTokenizer st = new StringTokenizer(line,"\t");
		com = st.nextToken().toUpperCase();
		while(st.hasMoreTokens()) {
			listParam.add(st.nextToken());
		}
	}
}
