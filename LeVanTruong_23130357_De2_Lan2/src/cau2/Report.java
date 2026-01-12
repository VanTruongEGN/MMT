package cau2;

import java.io.Serializable;
import java.time.LocalDate;

public class Report implements Serializable{
	private String numberAccount;
	private LocalDate date;
	private String action;
	private double value;
	public Report(String numberAccount, LocalDate date, String action, double value) {
		super();
		this.numberAccount = numberAccount;
		this.date = date;
		this.action = action;
		this.value = value;
	}
	public String getNumberAccount() {
		return numberAccount;
	}
	public void setNumberAccount(String numberAccount) {
		this.numberAccount = numberAccount;
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
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "numberAccount=" + numberAccount + ", date=" + date + ", action=" + action + ", value=" + value
				+ "\n";
	}
	
}
