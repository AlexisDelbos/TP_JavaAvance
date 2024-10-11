package fr.fms.advfile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class AdvEx3File {

		public static final String[] STARTER = { "entrée", "salade", "soupe", "quiche", "aucune" };
		public static final String[] DISHES = { "plats", "poulet", "boeuf", "poisson", "végétarien", "vegan", "aucun" };
		public static final String[] SIDE_DISH = { "accompagnements", "riz", "pates", "frites", "légumes", "aucun" };
		public static final String[] DRINKS = { "boissons", "eau plate", "eau gazeuze", "soda", "vin", "aucune" };
		public static final String[] DESSERTS = { "desserts", "tarte maison", "mousse au chocolat", "tiramisu", "aucun" };
	 
		public static Map<String, Price> menu = new HashMap<String, Price>();
	 
		public static void main(String[] args) {
	 
			///////////////////
	 
			menu.put("salade", new Price("salade", 5.50));
			menu.put("soupe", new Price("soupe", 4.00));
			menu.put("quiche", new Price("quiche", 6.00));
			menu.put("aucune", new Price("aucune", 0.00));
	 
			menu.put("poulet", new Price("poulet", 12.00));
			menu.put("boeuf", new Price("boeuf", 15.00));
			menu.put("poisson", new Price("poisson", 14.00));
			menu.put("végétarien", new Price("végétarien", 10.00));
			menu.put("vegan", new Price("vegan", 11.00));
			menu.put("aucun", new Price("aucun", 0.00));
	 
			menu.put("riz", new Price("riz", 3.00));
			menu.put("pates", new Price("pates", 3.50));
			menu.put("frites", new Price("frites", 2.50));
			menu.put("légumes", new Price("légumes", 4.00));
			menu.put("aucun", new Price("aucun", 0.00));
	 
			menu.put("eau plate", new Price("eau plate", 1.00));
			menu.put("eau gazeuze", new Price("eau gazeuze", 1.50));
			menu.put("soda", new Price("soda", 2.00));
			menu.put("vin", new Price("vin", 5.00));
			menu.put("aucune", new Price("aucune", 0.00));
	 
			menu.put("tarte maison", new Price("tarte maison", 4.50));
			menu.put("mousse au chocolat", new Price("mousse au chocolat", 5.00));
			menu.put("tiramisu", new Price("tiramisu", 5.50));
			menu.put("aucun", new Price("aucun", 0.00));
	 
			//////////////////////
			System.out.println("bonjour, combien de menus souhaitez vous ?");
	        Scanner scan = new Scanner(System.in);
	        int nbMenu = 0;
	        
	        while (true) {
	            try {
	                if (!scan.hasNextInt()) {
	                    throw new InputMismatchException("Il faut un chiffre s'il vous plait");
	                }
	                nbMenu = scan.nextInt();
	                if (nbMenu <= 0) {
	                    throw new IllegalArgumentException("Obligé au dessus de 0.");
	                }
	                break; 
	            } catch (InputMismatchException e) {
	                System.out.println(e.getMessage());
	                scan.next();
	            }
	        }
			ArrayList<String> order = new ArrayList<String>();
	 
			try (BufferedWriter file = new BufferedWriter(new FileWriter("order.txt"))) {
				double totalPrice = 0;
				for (int i = 0; i < nbMenu; i++) {
	 
					System.out.println("Commande numéro " + (i + 1));
					file.write("**********Résumé de la commande " + (i + 1) + "**********\n");
	 
					int result = getInfos(scan, STARTER[0]);
	 
					if (STARTER.length - 1 > result) {
						order.add(STARTER[result]);
						totalPrice += menu.get(STARTER[result]).getPriceItem();
						file.write(STARTER[result] + " : " + menu.get(STARTER[result]).getPriceItem() + "\n");
	 
						result = getInfos(scan, STARTER[0]);
					}
					if (DISHES.length - 1 > result) {
						order.add(DISHES[result]);
						totalPrice += menu.get(DISHES[result]).getPriceItem();
						file.write(DISHES[result] + " : " + menu.get(DISHES[result]).getPriceItem() + "\n");
						result = getInfos(scan, DISHES[0]);
					}
					if (SIDE_DISH.length - 1 > result) {
						order.add(SIDE_DISH[result]);
						totalPrice += menu.get(SIDE_DISH[result]).getPriceItem();
						file.write(SIDE_DISH[result] + " : " + menu.get(SIDE_DISH[result]).getPriceItem() + "\n");
						result = getInfos(scan, SIDE_DISH[0]);
					}
					if (DRINKS.length - 1 > result) {
						order.add(DRINKS[result]);
						totalPrice += menu.get(DRINKS[result]).getPriceItem();
						file.write(DRINKS[result] + " : " + menu.get(DRINKS[result]).getPriceItem() + "\n");
						result = getInfos(scan, DRINKS[0]);
					}
					if (DESSERTS.length - 1 > result) {
						order.add(DESSERTS[result]);
						totalPrice += menu.get(DESSERTS[result]).getPriceItem();
						file.write(DESSERTS[result] + " : " + menu.get(DESSERTS[result]).getPriceItem() + "\n");
						result = getInfos(scan, DESSERTS[0]);
					}
					System.out.println("Total : " + totalPrice);
					file.write("Total : " + totalPrice);
					totalPrice = 0;
					file.newLine();
					order.clear();
				}
				System.out.println("Fin des commandes");
			} catch (IOException e) {
				System.err.println("Impossible !" + e.getMessage());
			}
			scan.close();
		}
	 
		public static int getInfos(Scanner scan, String info) {
			System.out.println("choix " + info + " : ");
			int maxChoice = 0;
			if (info.equalsIgnoreCase(STARTER[0]))
				maxChoice = displayTable(STARTER);
			else if (info.equalsIgnoreCase(DISHES[0]))
				maxChoice = displayTable(DISHES);
			else if (info.equalsIgnoreCase(SIDE_DISH[0]))
				maxChoice = displayTable(SIDE_DISH);
			else if (info.equalsIgnoreCase(DRINKS[0]))
				maxChoice = displayTable(DRINKS);
			else if (info.equalsIgnoreCase(DESSERTS[0]))
				maxChoice = displayTable(DESSERTS);
			System.out.println("que souhaitez vous comme " + info + " ? [saisir le chiffre correspondant]");
	 
			int value = 0;
			 while (value < 1 || value > maxChoice) {
			        try {
			            if (!scan.hasNextInt()) {
			                throw new InputMismatchException("Saisie invalide : entrez un chiffre.");
			            }
			            value = scan.nextInt();
			            if (value < 1 || value > maxChoice) {
			                System.out.println("mauvaise saisie ! Choisissez un chiffre entre 1 et " + maxChoice);
			            }
			        } catch (InputMismatchException e) {
			            System.out.println(e.getMessage());
			            scan.next();
			        }
			    }
			    
			    return value;
			}
	 
		public static int displayTable(String[] table) {
			for (int i = 1; i < table.length; i++) {
				System.out.print("[" + i + " - " + table[i].toUpperCase() + "]");
			}
			System.out.println();
			return table.length - 1;
		}
	}
