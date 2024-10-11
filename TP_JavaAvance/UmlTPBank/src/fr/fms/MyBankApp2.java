package fr.fms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import fr.fms.business.IBankImpl;
import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.entities.Transaction;
import fr.fms.exceptions.AccountNotFoundException;
import fr.fms.exceptions.WithdrawalException;

public class MyBankApp2 {
    public static void main(String[] args) {
        IBankImpl bankJob = new IBankImpl();
        Scanner scanner = new Scanner(System.in);

        // Création de quelques clients et comptes pour démarrer
        Customer robert = new Customer(1, "dupont", "robert", "robert.dupont@xmail.com");
        Customer julie = new Customer(2, "jolie", "julie", "julie.jolie@xmail.com");
        Current firstAccount = new Current(100200300, new Date(), 1500, 200, robert);
        Saving secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);

        bankJob.addAccount(firstAccount);
        bankJob.addAccount(secondAccount);

        boolean appRunning = true;

        while (appRunning) {
            System.out.print("Veuillez entrer votre numéro de compte : ");
            long accountId = scanner.nextLong();

            try {
                // Vérification si le compte existe
                Account account = bankJob.consultAccount(accountId);
                System.out.println("Bienvenue, " + account.getCustomer().getName() + "! Que voulez-vous faire ?");

                boolean running = true;
                while (running) {
                    System.out.println("=== Menu ===");
                    System.out.println("1. Versement");
                    System.out.println("2. Retrait");
                    System.out.println("3. Virement");
                    System.out.println("4. Afficher les informations sur un compte");
                    System.out.println("5. Afficher la liste des opérations d'un compte");
                    System.out.println("6. Quitter");
                    System.out.print("Choisissez une option : ");

                    int choice = scanner.nextInt();

                    try {
                        switch (choice) {
                        	// Effectuer un versement
                            case 1: 
                                System.out.print("Saisissez le montant à verser : ");
                                double amountToDeposit = scanner.nextDouble();
                                bankJob.pay(accountId, amountToDeposit);
                                break;

                            // Effectuer un retrait
                            case 2:
                                System.out.print("Saisissez le montant à retirer : ");
                                double amountToWithdraw = scanner.nextDouble();
                                bankJob.withdraw(accountId, amountToWithdraw);
                                break;

                            // Faire un virement    
                            case 3: 
                                System.out.print("Saisissez l'ID du compte destinataire : ");
                                long accIdDest = scanner.nextLong();
                                System.out.print("Saisissez le montant à virer : ");
                                double amountToTransfer = scanner.nextDouble();
                                bankJob.transfert(accountId, accIdDest, amountToTransfer);
                                break;

                            // Afficher les informations sur un compte
                            case 4: 
                                Account accInfo = bankJob.consultAccount(accountId);
                                System.out.println("Détails du compte : " + accInfo);
                                break;

                            // Afficher la liste des opérations d'un compte
                            case 5: 
                                ArrayList<Transaction> transactions = bankJob.listTransactions(accountId);
                                System.out.println("Transactions pour le compte " + accountId + ":");
                                for (Transaction trans : transactions) {
                                    System.out.println(trans);
                                }
                                break;
                                
                            // Revenir a un numéro de compte
                            case 6: 
                                running = false;
                                break;

                            default:
                                System.out.println("Option invalide. Veuillez réessayer.");
                                break;
                        }
                    } catch (AccountNotFoundException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    } catch (WithdrawalException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                }
            } catch (AccountNotFoundException e) {
                System.out.println("Compte invalide : " + e.getMessage());
            }
        }

        scanner.close();
    }
}
