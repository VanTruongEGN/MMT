package cau2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISearch extends Remote{
	public boolean checkUser(String user) throws RemoteException;
	public boolean login(String user, String pass) throws RemoteException;
	public boolean deposit(String accountNumber, double value) throws RemoteException;
	public boolean withdraw(String accountNumber, double value) throws RemoteException;
	public List<Log> getLog(String accountNumber)throws RemoteException;
	public double getBalance(String accountNumber)throws RemoteException;
	public void insertLog(String accountNumber, String action, double value) throws RemoteException;
	public String getNumberAccount(String username) throws RemoteException;
}
