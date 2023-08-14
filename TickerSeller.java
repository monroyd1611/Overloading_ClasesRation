import java.util.Scanner;
import java.util.Random;

public class TicketSeller {
    private static final int TOTAL_TICKETS = 60;
    private static final int LOCALITIES_COUNT = 3;
    private static final int TICKETS_PER_LOCALITY = TOTAL_TICKETS / LOCALITIES_COUNT;
    private static final int[] TICKET_PRICES = {400, 695, 2350};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locality[] localities = initializeLocalities();

        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter DPI: ");
        String dpi = scanner.nextLine();
        System.out.println("Enter how many tickets you want: ");
        int CountsOfTicket = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter your max Budget: ");
        int maximBudget = Integer.parseInt(scanner.nextLine());

        TicketRequest request = new TicketRequest(name, dpi, CountsOfTicket, maximBudget);
        int ticket = generateRandomNumber(33000);
        if (isTicketaffordable(ticket)) {
            Locality locality = assignRandomLocality(localities);
            if (locality.validateAndSell(request)) {
                System.out.println("Tickets sold successfully!");
            } else {
                System.out.println("Tickets could not be sold");
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

    private static boolean isTicketaffordable(int ticket) {
        System.out.println(ticket);
        int a = generateRandomNumber(15000);
        System.out.println(a);
        int b = generateRandomNumber(15000);
        System.out.println(b);
        return (ticket + a + b) % 2 == 0;
    }

    private static Locality assignRandomLocality(Locality[] localities) {
        return localities[generateRandomNumber(LOCALITIES_COUNT) - 1];
    }

}

class TicketRequest {
    String name;
    String dpi;
    int CountsOfTicket;
    int maximBudget;

    public TicketRequest(String name, String dpi, int CountsOfTicket, int maximBudget) {
        this.name = name;
        this.dpi = dpi;
        this.CountsOfTicket = CountsOfTicket;
        this.maximBudget = maximBudget;
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
        if (request.maximBudget < price) {
            return false;
        }

        int ticketsToSell = Math.min(request.CountsOfTicket, availableTickets);
        availableTickets -= ticketsToSell;
        return true;
    }
}