package cau2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class SearchImpl extends UnicastRemoteObject implements ISearch{
	private final Dao dao;
	protected SearchImpl() throws RemoteException, ClassNotFoundException, SQLException {
		super();
		dao = Dao.getInstance();
	}

	@Override
	public boolean checkUser(String user) throws RemoteException {
		try {
			if(dao.checkUser(user)){
				return true;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return false;
	}

	@Override
	public boolean login(String user, String pass) throws RemoteException {
		try {
			if(dao.login(user, pass)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean deposit(String accountNumber, double value) throws RemoteException {
		try {
			return dao.deposit(accountNumber, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean withdraw(String accountNumber, double value) throws RemoteException {
		try {
			if(dao.withdraw(accountNumber,value)){
				return true;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return false;
	}

	@Override
	public List<Log> getLog(String accountNumber) throws RemoteException {
		try {
			return dao.getLog(accountNumber);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public double getBalance(String accountNumber) throws RemoteException {
		try {
			return dao.getBalance(accountNumber);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}

	@Override
	public void insertLog(String accountNumber, String action, double value) throws RemoteException {
		try {
			dao.insertLog(accountNumber, action, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public String getNumberAccount(String username) throws RemoteException {
		try {
			return dao.getNumberAccount(username);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
