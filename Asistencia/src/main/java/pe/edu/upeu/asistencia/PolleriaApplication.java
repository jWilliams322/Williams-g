package pe.edu.upeu.asistencia;

import java.util.Scanner;

public class PolleriaApplication {

    public static void main(String[] args) {
        Scanner KEVIN = new Scanner(System.in);

        System.out.println("===BIENVENIDOS A LA POLLERIA LA GRANJA===");
        System.out.println("1. POLLO ALA BRASSA");
        System.out.println("2. POLLO BROASTERS");
        System.out.println("3. SALCHIPAPA");
        System.out.println("4. BEBIDAS");
        System.out.println("ELIGE UNA OPCION DE COMIDA");
        int OPCIONCLIENTE = KEVIN.nextInt();

        if (OPCIONCLIENTE == 1) {
            System.out.println("===OPCIONES DE POLLO ALA BRASSA===");
            System.out.println("1. UN CUARTO");
            System.out.println("2. UN OCTAVO");
            System.out.println("3. ENTERO");
            System.out.println("4. MEDIO POLLO");
            System.out.println("ELIGE UNA OPCION DE POLLO");
            int OPCIONPOLLO = KEVIN.nextInt();

            int precioUnitario = 0;

            switch (OPCIONPOLLO) {
                case 1:
                    precioUnitario = 10;
                    break;
                case 2:
                    precioUnitario = 20;
                    break;
                case 3:
                    precioUnitario = 17;
                    break;
                case 4:
                    precioUnitario = 42;
                    break;
                default:
                    System.out.println("OPCION DE POLLO NO EXISTE");
                    KEVIN.close();
                    return;
            }

            System.out.println("¿Cuántas unidades quieres?");
            int cantidad = KEVIN.nextInt();

            int precioTotal = precioUnitario * cantidad;
            System.out.println("El precio total es: " + precioTotal + " SOLES");

        } else if (OPCIONCLIENTE == 2) {
            System.out.println("===OPCIONES DE POLLO BROASTERS===");
            System.out.println("1. UN CUARTO");
            System.out.println("2. UN OCTAVO");
            System.out.println("3. ENTERO");
            System.out.println("4. MEDIO POLLO");
            System.out.println("ELIGE UNA OPCION DE POLLO");
            int OPCIONBROASTER = KEVIN.nextInt();

            int precioUnitario = 0;

            switch (OPCIONBROASTER) {
                case 1:
                    precioUnitario = 10;
                    break;
                case 2:
                    precioUnitario = 20;
                    break;
                case 3:
                    precioUnitario= 17;
                    break;
                case 4:
                    precioUnitario = 42;
                    break;
                default:
                    System.out.println("OPCION DE POLLO NO EXISTE");
                    KEVIN.close();
                    return;
            }

            System.out.println("¿Cuántas unidades quieres?");
            int cantidad = KEVIN.nextInt();

            int precioTotal = precioUnitario * cantidad;
            System.out.println("El precio total es: " + precioTotal + " SOLES");

        } else if (OPCIONCLIENTE == 3) {
            System.out.println("===OPCIONES DE SALCHIPAPA===");
            System.out.println("1. UN CUARTO");
            System.out.println("2. UN OCTAVO");
            System.out.println("3. ENTERO");
            System.out.println("4. MEDIA SALCHIPAPA");
            System.out.println("ELIGE UNA OPCION DE SALCHIPAPA");
            int OPCIONSALCHIPAPA = KEVIN.nextInt();

            int precioUnitario = 0;

            switch (OPCIONSALCHIPAPA) {
                case 1:
                    precioUnitario = 10;
                    break;
                case 2:
                    precioUnitario = 20;
                    break;
                case 3:
                    precioUnitario = 17;
                    break;
                case 4:
                    precioUnitario = 42;
                    break;
                default:
                    System.out.println("OPCION DE SALCHIPAPA NO EXISTE");
                    KEVIN.close();
                    return;
            }

            System.out.println("¿Cuántas unidades quieres?");
            int cantidad = KEVIN.nextInt();

            int precioTotal = precioUnitario * cantidad;
            System.out.println("El precio total es: " + precioTotal + " SOLES");

        } else if (OPCIONCLIENTE == 4) {
            System.out.println("===OPCIONES DE BEBIDAS===");
            System.out.println("1. GASEOSA 500ml - 5 SOLES");
            System.out.println("2. JUGO NATURAL - 7 SOLES");
            System.out.println("3. AGUA MINERAL - 3 SOLES");
            System.out.println("ELIGE UNA OPCION DE BEBIDA");
            int OPCIONBEBIDA = KEVIN.nextInt();

            int precioUnitario = 0;

            switch (OPCIONBEBIDA) {
                case 1:
                    precioUnitario = 5;
                    break;
                case 2:
                    precioUnitario = 7;
                    break;
                case 3:
                    precioUnitario = 3;
                    break;
                default:
                    System.out.println("OPCION DE BEBIDA NO EXISTE");
                    KEVIN.close();
                    return;
            }

            System.out.println("¿Cuántas unidades quieres?");
            int cantidad = KEVIN.nextInt();

            int precioTotal = precioUnitario * cantidad;
            System.out.println("El precio total es: " + precioTotal + " SOLES");

        } else {
            System.out.println("NO EXISTE OPCION DE CLIENTE");
        }

        KEVIN.close();
    }
}


