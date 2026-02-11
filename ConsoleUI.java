import java.util.Scanner;

/*
 Simple console interface for manual testing.
 Lets user type clientId and see allow/block decision.
*/
public class ConsoleUI {

    public static void start(RateLimiterEngine engine) {

        // try-with-resources → scanner auto closed
        try (Scanner sc = new Scanner(System.in)) {

            System.out.println("=== Rate Limiter Console ===");

            while (true) {

                // Read client id
                System.out.print("\nEnter client id (or exit): ");
                String id = sc.nextLine();

                // Exit condition
                if (id.equalsIgnoreCase("exit"))
                    break;

                // Ask engine for decision
                boolean ok = engine.allow(id);

                // Print result
                if (ok)
                    System.out.println("Request ALLOWED");
                else
                    System.out.println("Request BLOCKED");
            }

            // Print summary metrics
            engine.printMetrics();
        }
    }
}
