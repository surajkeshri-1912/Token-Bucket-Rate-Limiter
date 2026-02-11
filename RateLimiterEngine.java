import java.util.concurrent.ConcurrentHashMap;

/*
 Core engine that manages rate limiting per client.
 Each client gets its own TokenBucket.
 Map is concurrent → safe under multi-threaded access.
*/
public class RateLimiterEngine {

    // Map: clientId → token bucket
    private final ConcurrentHashMap<String, TokenBucket> buckets =
            new ConcurrentHashMap<>();

    // Global metrics tracker
    private final Metrics metrics = new Metrics();

    private final int capacity;
    private final double refillRate;

    public RateLimiterEngine(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
    }

    /*
     Main decision method.
     Returns true if request allowed, false if rejected.
    */
    public boolean allow(String clientId) {

        // Get existing bucket or create new one atomically
        TokenBucket bucket = buckets.computeIfAbsent(
                clientId,
                id -> new TokenBucket(capacity, refillRate)
        );

        // Ask bucket for decision
        boolean allowed = bucket.allowRequest();

        // Update metrics
        if (allowed) metrics.markAllowed();
        else metrics.markRejected();

        return allowed;
    }

    // Print collected metrics
    public void printMetrics() {
        metrics.printStats();
    }
}
