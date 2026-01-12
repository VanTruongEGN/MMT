package cau2;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
	public static void main(String[] args) throws AccessException, RemoteException, ClassNotFoundException, SQLException {
		Registry reg = LocateRegistry.createRegistry(1090);
		reg.rebind("Search", new SearchImpl());
	}
}
