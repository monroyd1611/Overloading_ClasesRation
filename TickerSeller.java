//Carlos Angel 23310
//Diego Monroy 23318
//Programación Oriendata a Objetos, Franco Augusto
//Ejercicio 1. Overloading y relaciones entre clases
//Own knowledge

import java.util.Scanner;
import java.util.Random;

public class TicketSeller {
    private static final int TOTAL_TICKETS = 60;
    private static final int LOCALITIES_COUNT = 3;
    private static final int TICKETS_PER_LOCALITY = TOTAL_TICKETS / LOCALITIES_COUNT;
    private static final int[] TICKET_PRICES = {400, 695, 2350};
    private static boolean verify = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locality[] localities = initializeLocalities(); // Initialize localities and their ticket information
        TicketRequest request = null; // Initialize ticket request object for buyer

        while (verify) {
            // Display menu options
            System.out.println("");
            System.out.println("1. Enter as a new buyer");
            System.out.println("2. Enter as the same buyer");
            System.out.println("3. Check the number of tickets available");
            System.out.println("4. Check the number of tickets available for a location");
            System.out.println("5. Check accounting cash");
            System.out.println("6. Exit");
            System.out.println("Select a number from the menu");
            
            int menu = Integer.parseInt(scanner.nextLine());

            if (menu == 1) {
                // Collect buyer information to create a new ticket request
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                System.out.println("Enter DPI: ");
                String dpi = scanner.nextLine();
                System.out.println("Enter how many tickets you want: ");
                int countsOfTicket = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter your max Budget: ");
                int maxBudget = Integer.parseInt(scanner.nextLine());

                request = new TicketRequest(name, dpi, countsOfTicket, maxBudget);
            }

            if (request != null && (menu == 1 || menu == 2)) {
                int ticket = generateRandomNumber(33000);
                int z = generateRandomNumber(3); // Generate a random locality index
                
                if (isTicketAffordable(ticket)) {
                    // Attempt to sell tickets based on a random locality
                    if (z == 1) {
                        // If ticket is affordable and there are available tickets, sell the tickets
                        if (localities[0].validateAndSell(request)) {
                            System.out.println("Tickets sold successfully! You are in Locality #1");
                            int maxBudget1 = request.maxBudget;
                            request.maxBudget -= localities[0].price * request.countsOfTicket;
                            int price = maxBudget1 - request.maxBudget;
                            System.out.println("The total of your tickets is: $" + price);
                        } else {
                            System.out.println("Tickets could not be sold");
                        }
                    } else if (z == 2) {
                        // Similar process for another locality
                        if (localities[1].validateAndSell(request)) {
                            System.out.println("Tickets sold successfully! You are in Locality #5");
                            int maxBudget1 = request.maxBudget;
                            request.maxBudget -= localities[1].price * request.countsOfTicket;
                            int price = maxBudget1 - request.maxBudget;
                            System.out.println("The total of your tickets is: $" + price);
                        } else {
                            System.out.println("Tickets could not be sold");
                        }
                    } else if (z == 3) {
                        // And yet another locality
                        if (localities[2].validateAndSell(request)) {
                            System.out.println("Tickets sold successfully! You are in Locality #10");
                            int maxBudget1 = request.maxBudget;
                            request.maxBudget -= localities[2].price * request.countsOfTicket;
                            int price = maxBudget1 - request.maxBudget;
                            System.out.println("The total of your tickets is: $" + price);
                        } else {
                            System.out.println("Tickets could not be sold");
                        }
                    }
                } else {
                    // If ticket is not affordable, cannot sell tickets
                    System.out.println("Tickets could not be sold");
                }
            }

            if (menu == 3) {
                // Display the number of tickets sold and available for each locality
                System.out.println("Total tickets sold and available for each locality:");
                for (int i = 0; i < LOCALITIES_COUNT; i++) {
                    System.out.println("Locality #" + (i + 1) + ": Sold " + (TICKETS_PER_LOCALITY - localities[i].availableTickets) +" tickets, Available " + localities[i].availableTickets + " tickets");
                }
            }

            if (menu == 4) {
                // Check availability for a specific locality
                System.out.println("Enter the locality number (1=Locality1, 2=Locality5, 3=Locality10) to check availability:");
                int selectedLocality = Integer.parseInt(scanner.nextLine());
                if (selectedLocality > 0 && selectedLocality < LOCALITIES_COUNT + 1) {
                    if(selectedLocality == 1){
                        System.out.println("Available tickets for Locality #1: " + localities[selectedLocality-1].availableTickets);
                    }
                    if(selectedLocality == 2){
                        System.out.println("Available tickets for Locality #5: " + localities[selectedLocality-1].availableTickets);
                    }
                    if(selectedLocality == 3){
                        System.out.println("Available tickets for Locality #10: " + localities[selectedLocality-1].availableTickets);
                    }
                } else {
                    System.out.println("Invalid locality number.");
                }
            }

            if (menu == 5) {
                // Calculate and display total revenue from ticket sales
                int totalRevenue = 0;
                for (int i = 0; i < LOCALITIES_COUNT; i++) {
                    int soldTickets = TICKETS_PER_LOCALITY - localities[i].availableTickets;
                    totalRevenue += soldTickets * localities[i].price;
                }
                System.out.println("Total revenue generated: $" + totalRevenue);
            }
            if (menu == 6) {
                // Exit the loop, ending the ticket selling process
                verify = false;
            }
        }
    }

    private static Locality[] initializeLocalities() {
        Locality[] localities = new Locality[LOCALITIES_COUNT];
        for (int i = 0; i < localities.length; i++) {
            localities[i] = new Locality(i + 1, TICKETS_PER_LOCALITY, TICKET_PRICES[i]);
        }
        return localities;
    }

    private static int generateRandomNumber(int range) {
        Random rand = new Random();
        return rand.nextInt(range) + 1;
    }

    private static boolean isTicketAffordable(int ticket) {
        int a = generateRandomNumber(15000);
        int b = generateRandomNumber(15000);
        return (ticket + a + b) % 2 == 0;
    }
}

class TicketRequest {
    String name;
    String dpi;
    int countsOfTicket;
    int maxBudget;

    public TicketRequest(String name, String dpi, int countsOfTicket, int maxBudget) {
        this.name = name;
        this.dpi = dpi;
        this.countsOfTicket = countsOfTicket;
        this.maxBudget = maxBudget;
    }
}

class Locality {
    int id;
    int availableTickets;
    int price;

    public Locality(int id, int availableTickets, int price) {
        this.id = id;
        this.availableTickets = availableTickets;
        this.price = price;
    }

    public boolean validateAndSell(TicketRequest request) {
        if (availableTickets == 0) {
            return false;
        }
        if (request.maxBudget < price) {
            return false;
        }

        int ticketsToSell = Math.min(request.countsOfTicket, availableTickets);
        availableTickets -= ticketsToSell;
        return true;
    }
}