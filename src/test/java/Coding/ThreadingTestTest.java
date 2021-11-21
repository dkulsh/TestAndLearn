package Coding;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

class ThreadingTestTest {

//    Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
    Map<String, String> map = new HashMap<>();
//    Map<String, String> map = new ConcurrentHashMap<>();

    AtomicBoolean threadRunning = new AtomicBoolean();
    AtomicInteger threadOverlaps = new AtomicInteger();
    CountDownLatch latch = new CountDownLatch(1);

    int threads = 10;
    ExecutorService service = Executors.newFixedThreadPool(threads);
    String[] values = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

    @Test
    void testPut(){

        List<Future<String>> futureList = new ArrayList<>();

        for (int i = 0; i < threads; i++){

            String value = values[i];

            Future<String> stringFuture = service.submit(() -> {

                latch.await();

                if (threadRunning.get()) { // One thread overlaps another
                    threadOverlaps.incrementAndGet();
                }

                threadRunning.set(true);

                Thread.sleep(50);
                String returnedValue = map.put("1", value);

                threadRunning.set(false);

                return returnedValue;

            });

            futureList.add(stringFuture);
        }

        latch.countDown();

        System.out.println("We had " + threadOverlaps + " overlaps during the put method");
        assert(threadOverlaps.get() > 0);

        Set<String> stringSet = futureList.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toSet());
        System.out.println("Set size " + stringSet.size() + ". Set -> " + stringSet);
        assert(stringSet.size() == threads);
    }

    @Test
    void testRemove() {

        map.put("1", "Original");
        List<Future<String>> futureList = new ArrayList<>();

        for (int i = 0; i < threads; i++){

            String value = values[i];

            Future<String> stringFuture = service.submit(() -> {

                latch.await();

                if (threadRunning.get()) { // One thread overlaps another
                    threadOverlaps.incrementAndGet();
                }

                threadRunning.set(true);

                Thread.sleep(50);
                String returnedValue = map.remove("1");

                threadRunning.set(false);

                return returnedValue;

            });

            futureList.add(stringFuture);
        }

        latch.countDown();

        System.out.println("We had " + threadOverlaps + " overlaps during the put method");
        assert(threadOverlaps.get() > 0);

        List<String> stringList = futureList.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println("List size " + stringList.size() + ". List -> " + stringList);

        assert(Collections.frequency(stringList, "Original") == 1);
    }

}