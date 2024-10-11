/**
 * Version 1.0 d'une appli bancaire simplifiée offrant la possibilité de créer des clients, des comptes bancaires associés et des opérations ou
 * transactions bancaires sur ceux-ci telles que : versement, retrait ou virement 
 * + permet d'afficher l'historique des transactions sur un compte
 * + la gestion des cas particuliers est rudimentaire ici puisque la notion d'exception n'a pas encore été abordée
 * 
 * @author El babili - 2023
 * 
 */

package fr.fms;

import java.util.Date;

import fr.fms.business.IBankImpl;
import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.entities.Transaction;
import fr.fms.exceptions.AccountNotFoundException;
import fr.fms.exceptions.WithdrawalException;

public class MyBankApp {    
    public static void main(String[] args) {
        // représente l'activité de notre banque
        IBankImpl bankJob = new IBankImpl();
        
        System.out.println("création puis affichage de 2 comptes bancaires");
        Customer robert = new Customer(1, "dupont", "robert", "robert.dupont@xmail.com");
        Customer julie = new Customer(2, "jolie", "julie", "julie.jolie@xmail.com");        
        Current firstAccount = new Current(100200300, new Date(), 1500, 200 , robert);
        Saving secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);
        
        System.out.println(firstAccount);
        System.out.println(secondAccount);        
        
        bankJob.addAccount(firstAccount);
        bankJob.addAccount(secondAccount);
        
        // banquier ou client
        try {
            bankJob.pay(firstAccount.getAccountId(), 500);        // versement de 500 euros sur le compte de robert
            bankJob.pay(secondAccount.getAccountId(), 1000);    // versement de 1000 euros sur le compte de julie
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur lors du versement : " + e.getMessage());
        }
        
        // banquier ou client
        try {
            bankJob.withdraw(100200300, 250);            // retrait de 250 euros sur le compte de robert
            bankJob.withdraw(200300400, 400);            // retrait de 400 euros sur le compte de julie
        } catch (WithdrawalException | AccountNotFoundException e) {
            System.err.println("Erreur lors du retrait : " + e.getMessage());
        }
        
        // banquier ou client
        try {
            bankJob.transfert(firstAccount.getAccountId(), 200300400, 200);        // virement de robert chez julie de 200
        } catch (WithdrawalException e) {
            System.err.println("Erreur lors du virement : " + e.getMessage());
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur lors du virement : " + e.getMessage());
        }
        
        System.out.println("----------------------------------------------------------");
        try {
            System.out.println("solde de " + firstAccount.getCustomer().getName() + " : " + bankJob.consultAccount(firstAccount.getAccountId()).getBalance());
            System.out.println("solde de " + secondAccount.getCustomer().getName() + " : " + bankJob.consultAccount(secondAccount.getAccountId()).getBalance());
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur lors de la consultation de compte : " + e.getMessage());
        }
        
        // Test du compte inexistant
        try {
            bankJob.consultAccount(111111);        // test du compte inexistant
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        
        // Test capacité retrait dépassée
        try {
            bankJob.withdraw(100200300, 10000);    // test capacité retrait dépassée
        } catch (WithdrawalException | AccountNotFoundException e) {
            System.err.println("Erreur lors du retrait : " + e.getMessage());
        }
        
        // Test virement sur le même compte
        try {
            bankJob.transfert(100200300, 100200300, 50000);        // test virement sur le même compte
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur lors du virement : " + e.getMessage());
        } catch (WithdrawalException e) {
            System.err.println("Erreur lors du virement : " + e.getMessage());
        }
        
        // banquier
        bankJob.addAccount(firstAccount);    // test rajout du même compte au même client
        bankJob.addAccount(new Current(300400500, new Date(), 750, 150 , julie));    // ajout nouveau compte à Julie        
        System.out.println("\n-----------------------Liste des comptes de ma banque-----------------------------------");
        for(Account acc : bankJob.listAccounts())
            System.out.println(acc);
        System.out.println("\n-----------------------Liste des comptes de julie-----------------------------------");
        for(Account acc : julie.getListAccounts()) {
            System.out.println(acc);
        }
        
     // banquier ou client
        try {
            System.out.println("\n-------------------liste des transactions de l'unique compte de robert------------------------");
            for (Transaction trans : bankJob.listTransactions(100200300)) {
                System.out.println(trans);
            }
            
            System.out.println("-------------------liste des transactions du compte N° 200300400 de Julie------------------------");
            for (Transaction trans : bankJob.listTransactions(200300400)) {
                System.out.println(trans);
            }
        } catch (AccountNotFoundException e) {
            System.err.println("Erreur lors de l'affichage des transactions : " + e.getMessage());
        }
    }

}
