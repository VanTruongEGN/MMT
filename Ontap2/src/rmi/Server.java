package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class Server{
	public static void main(String[] args) throws RemoteException, ClassNotFoundException, SQLException {
		Registry reg = LocateRegistry.createRegistry(12345);
		reg.rebind("SEARCH", new Dao());
	}
}
