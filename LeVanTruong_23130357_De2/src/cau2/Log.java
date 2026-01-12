package cau2;

import java.io.Serializable;
import java.time.LocalDate;

public class Log implements Serializable{
	private String accountNumber;
	private LocalDate date;
	private String action;
	private double value;
	public Log(String accountNumber, LocalDate date, String action, double value) {
		super();
		this.accountNumber = accountNumber;
		this.date = date;
		this.action = action;
		this.value = value;
	}
	public Log() {
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public double getvalue() {
		return value;
	}
	public void setvalue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "accountNumber=" + accountNumber + ", date=" + date + ", action=" + action + ", value="
				+ value + "\n";
	}
	
}
