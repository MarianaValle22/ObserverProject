package edu.unisabana.dyas.patterns.observer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import edu.unisabana.dyas.patterns.observer.impl.ConfigurationManager;
import edu.unisabana.dyas.patterns.observer.impl.observers.DateFormatObserver;
import edu.unisabana.dyas.patterns.observer.impl.observers.MoneyFormatObserver;

public class ObserverMain {

    public static void main(String[] args) {
        ConfigurationManager conf = ConfigurationManager.getInstance();

        //Se dan de alta lo observers
        DateFormatObserver dateFormatObserver = new DateFormatObserver();
        MoneyFormatObserver moneyFormatObserver = new MoneyFormatObserver();
        conf.addObserver(dateFormatObserver);
        conf.addObserver(moneyFormatObserver);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Cambiar formato de fecha");
            System.out.println("2. Cambiar formato de dinero");
            System.out.println("3. Salir");

            String option = UserInput(scanner);
            if (option == null) {
                break;
            }

            switch (option) {
                case "1":
                    changeDateFormat(scanner, conf);
                    break;
                case "2":
                    changeMoneyFormat(scanner, conf);
                    break;
                case "3":
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
                    break;
            }
        }
        /*
        //Se establecen los valores por default.
        conf.setDefaultDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
        conf.setMoneyFormat(new DecimalFormat("##.00"));
        System.out.println("Established configuration");


        System.out.println("");

        //Se cambia la configuracion
        conf.setDefaultDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        conf.setMoneyFormat(new DecimalFormat("###,#00.00"));
        System.out.println("");

        //Se realiza otro cambio en la configuración.
        conf.setDefaultDateFormat(new SimpleDateFormat("MM/yyyy/dd"));
        conf.setMoneyFormat(new DecimalFormat("###,#00"));

        conf.removeObserver(dateFormatObserver);
        conf.removeObserver(moneyFormatObserver);
        System.out.println("");

        //Se realiza otro cambio en la configuración.
        conf.setDefaultDateFormat(new SimpleDateFormat("MM/yyyy"));
        conf.setMoneyFormat(new DecimalFormat("###,##0.00"));
         */
    }

    private static void changeDateFormat(Scanner scanner, ConfigurationManager conf) {
        System.out.println("Ingrese un nuevo formato de fecha (ejemplo: dd/MM/yyyy):");
        String newDateFormat = UserInput(scanner);
        if (newDateFormat == null) {
            return;
        }else if (!newDateFormat.matches("[dMy/\\-]+")) {
            System.out.println("El formato de fecha ingresado es inválido, intente nuevamente.");
            return;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(newDateFormat);
            conf.setDefaultDateFormat(dateFormat);
        } catch (IllegalArgumentException e) {
            System.out.println("El formato de fecha ingresado es inválido, intente nuevamente.");
        }
    }

    private static void changeMoneyFormat(Scanner scanner, ConfigurationManager conf){
        System.out.println("Ingrese un nuevo formato de dinero (ejemplo: ##.00):");
        String newMoneyFormat = UserInput(scanner);
        if (newMoneyFormat == null) {
            return;
        } else if (!newMoneyFormat.matches("[#,0.]+")) {
            System.out.println("El formato de dinero ingresado es inválido, intente nuevamente.");
            return;
        }
        try {
            NumberFormat moneyFormat = new DecimalFormat(newMoneyFormat);
            conf.setMoneyFormat(moneyFormat);
        } catch (IllegalArgumentException e) {
            System.out.println("El formato de dinero ingresado es inválido, intente nuevamente.");
        }

    }

    private static String UserInput(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            System.out.println("No hay entrada disponible. Terminando el programa.");
            return null;
        }
        return scanner.nextLine().trim();
    }
}