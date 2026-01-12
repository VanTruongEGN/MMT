package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISearch extends Remote{
	public boolean checkUser(String username)throws RemoteException;
	public boolean login(String username, String pass)throws RemoteException;
	public Student findById(int id)throws RemoteException;
	public List<Student> findByName(String name)throws RemoteException;

}
