package fr.fms.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Transaction;
import fr.fms.entities.Transfert;
import fr.fms.entities.Withdrawal;
import fr.fms.exceptions.AccountNotFoundException;
import fr.fms.exceptions.WithdrawalException;

public class IBankImpl implements IBank {
    private HashMap<Long, Account> accounts;
    private HashMap<Long, Customer> customers;
    private long numTransactions;

    public IBankImpl() {
        accounts = new HashMap<Long, Account>();
        customers = new HashMap<Long, Customer>();
        numTransactions = 1;
    }

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        Customer customer = account.getCustomer();
        customers.put(customer.getCustomerId(), customer);
        addAccountToCustomer(customer, account);
    }

    @Override
    public Account consultAccount(long accountId) throws AccountNotFoundException {
        Account account = accounts.get(accountId);
        if (account == null)
            throw new AccountNotFoundException("Le compte avec l'ID " + accountId + " n'existe pas !");
        return account;
    }

    @Override
    public void pay(long accountId, double amount) throws AccountNotFoundException {
        Account account = consultAccount(accountId);
        account.setBalance(account.getBalance() + amount);
        Transaction trans = new Transfert(numTransactions++, new Date(), amount, accountId);
        account.getListTransactions().add(trans);
    }

    @Override
    public boolean withdraw(long accountId, double amount) throws AccountNotFoundException, WithdrawalException {
        Account account = consultAccount(accountId);
        double capacity = account instanceof Current ? account.getBalance() + ((Current) account).getOverdraft() : account.getBalance();

        if (amount > capacity) {
            throw new WithdrawalException("Retrait impossible : solde insuffisant !");
        }

        account.setBalance(account.getBalance() - amount);
        Transaction trans = new Withdrawal(numTransactions++, new Date(), amount, accountId);
        account.getListTransactions().add(trans);

        return true;
    }

    @Override
    public void transfert(long accIdSrc, long accIdDest, double amount) throws WithdrawalException, AccountNotFoundException {
        if (accIdSrc == accIdDest) {
            System.out.println("Vous ne pouvez pas retirer et verser sur le même compte !");
        } else {
            withdraw(accIdSrc, amount);
            pay(accIdDest, amount);
        }
    }

    @Override
    public ArrayList<Transaction> listTransactions(long accountId) throws AccountNotFoundException {
        return consultAccount(accountId).getListTransactions();
    }

    public ArrayList<Account> listAccounts() {
        return new ArrayList<Account>(accounts.values());
    }

    private void addAccountToCustomer(Customer customer, Account account) {
        boolean exist = false;
        for (Account acc : customer.getListAccounts()) {
            if (acc.getAccountId() == account.getAccountId()) {
                exist = true;
                break;
            }
        }
        if (!exist)
            customer.getListAccounts().add(account);
    }
}
