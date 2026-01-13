package cau2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class SearchImpl extends UnicastRemoteObject implements ISearch{
	private Dao dao;
	protected SearchImpl() throws RemoteException, ClassNotFoundException, SQLException {
		super();
		dao = Dao.getInstance();
	}

	@Override
	public boolean checkUser(String username) throws RemoteException {
		try {
			return dao.checkUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean login(String username, String pass) throws RemoteException {
		try {
			return dao.login(username, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getNumberAccount(String username) throws RemoteException {
		try {
			return dao.getNumberAccount(username);
		} catch (Exception e) {
			e.printStackTrace();		}
		return null;
	}

	@Override
	public double getBalance(String numberAccount) throws RemoteException {
		try {
			return dao.getBalance(numberAccount);
		} catch (Exception e) {
			e.printStackTrace();		}
		return -1;
	}

	@Override
	public boolean deposit(String numberAccount, double value) throws RemoteException {
		try {
			return dao.deposit(numberAccount, value);
		} catch (Exception e) {
			e.printStackTrace();		}
		return false;
	}

	@Override
	public boolean withdraw(String numberAccount, double value) throws RemoteException {
		try {
			return dao.withdraw(numberAccount, value);
		} catch (Exception e) {
			e.printStackTrace();		}
		return false;
	}

	@Override
	public List<Report> getReport(String numberAccount) throws RemoteException {
		try {
			return dao.getReport(numberAccount);
		} catch (Exception e) {
			e.printStackTrace();		}
		return null;
	}

	@Override
	public void insertReport(String numberAccount, String action, double value) {
		try {
			dao.insertReport(numberAccount, action, value);
		} catch (Exception e) {
			e.printStackTrace();		}
		
	}

}
