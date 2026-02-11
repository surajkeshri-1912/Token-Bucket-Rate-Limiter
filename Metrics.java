import java.util.concurrent.atomic.AtomicLong;

/*
 Thread-safe metrics collector.
 Uses AtomicLong so increments are lock-free and safe
 under concurrent updates.
*/
public class Metrics {

    // Count of allowed requests
    private final AtomicLong allowed = new AtomicLong();

    // Count of rejected requests
    private final AtomicLong rejected = new AtomicLong();

    // Mark a request as allowed
    public void markAllowed() {
        allowed.incrementAndGet();
    }

    // Mark a request as rejected
    public void markRejected() {
        rejected.incrementAndGet();
    }

    // Print final statistics
    public void printStats() {
        long a = allowed.get();
        long r = rejected.get();
        long total = a + r;

        System.out.println("\n==== Metrics ====");
        System.out.println("Allowed : " + a);
        System.out.println("Rejected: " + r);
        System.out.println("Total   : " + total);

        // Compute rejection percentage
        if (total > 0) {
            System.out.println("Reject %: " + (r * 100.0 / total));
        }
    }
}
