import java.util.concurrent.*;

/*
 Entry point of the application.
 Simulates concurrent client requests using a thread pool
 and then starts an interactive console UI.
*/
public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        // Create rate limiter with:
        // capacity = 10 tokens
        // refill rate = 5 tokens per second
        RateLimiterEngine engine =
                new RateLimiterEngine(10, 5);

        // Thread pool to simulate concurrent traffic
        ExecutorService pool = Executors.newFixedThreadPool(6);

        // Task representing repeated requests from same client
        Runnable task = () -> {
            for (int i = 0; i < 30; i++) {

                // Ask engine whether request is allowed
                boolean ok = engine.allow("userA");

                // Print thread + decision
                System.out.println(Thread.currentThread().getName()
                        + " -> " + ok);

                // Small delay between requests
                try { Thread.sleep(80); }
                catch (Exception ignored) {}
            }
        };

        // Submit same task from multiple workers
        for (int i = 0; i < 4; i++)
            pool.submit(task);

        // Stop accepting new tasks
        pool.shutdown();

        // Wait for workers to finish
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // Start manual console testing UI
        ConsoleUI.start(engine);
    }
}
