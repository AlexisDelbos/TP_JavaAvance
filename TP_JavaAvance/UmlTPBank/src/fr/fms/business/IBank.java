package fr.fms.business;

import java.util.ArrayList;

import fr.fms.entities.Account;
import fr.fms.entities.Transaction;
import fr.fms.exceptions.AccountNotFoundException;
import fr.fms.exceptions.WithdrawalException; // Assurez-vous que cela est importé

public interface IBank {
	public void addAccount(Account account); // ajoute un compte associé à un client à notre banque

	public Account consultAccount(long accountId) throws AccountNotFoundException; // renvoi le compte correspondant à
																					// l'id

	public void pay(long accountId, double amount) throws AccountNotFoundException; // faire un versement sur un compte

	public boolean withdraw(long accountId, double amount) throws AccountNotFoundException, WithdrawalException; // faire
																													// un
																													// retrait
																													// sur
																													// un
																													// compte

    public void transfert(long accIdSrc, long accIdDest, double amount) throws WithdrawalException, AccountNotFoundException;

	public ArrayList<Transaction> listTransactions(long accountId) throws AccountNotFoundException;

}
