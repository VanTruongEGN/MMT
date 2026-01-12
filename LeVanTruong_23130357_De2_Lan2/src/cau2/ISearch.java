package cau2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISearch extends Remote{
	public boolean checkUser(String username) throws RemoteException;
	public boolean login(String username, String pass) throws RemoteException;
	public String getNumberAccount(String username)throws RemoteException;
	public double getBalance(String numberAccount) throws RemoteException;
	public boolean deposit(String numberAccount, double value) throws RemoteException;
	public boolean withdraw(String numberAccount, double value) throws RemoteException;
	public List<Report> getReport(String numberAccount) throws RemoteException;
	public void insertReport(String numberAccount, String action, double value)throws RemoteException;
}
