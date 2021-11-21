package Coding

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.lang.InterruptedException
import java.util.concurrent.ExecutionException
import java.util.stream.Collectors
import kotlin.jvm.JvmStatic
import Coding.ThreadingTest
import java.util.*
import java.util.concurrent.Future

class ThreadingTest {
//    Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
    var map: MutableMap<String, String> = HashMap()
//    Map<String, String> map = new ConcurrentHashMap<>();

    var threadRunning = AtomicBoolean()
    var threadOverlaps = AtomicInteger()
    var latch = CountDownLatch(1)
    var threads = 10
    var service = Executors.newFixedThreadPool(threads)
    var values = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
            "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty")

    fun testPut() {
        val futureList: MutableList<Future<String?>> = ArrayList()
        for (i in 0 until threads) {
            val value = values[i]
            val stringFuture = service.submit<String?> {
                latch.await()
                if (threadRunning.get()) { // One thread overlaps another
                    threadOverlaps.incrementAndGet()
                }
                threadRunning.set(true)
                Thread.sleep(50)
                val returnedValue = map.put("1", value)
                threadRunning.set(false)
                returnedValue
            }
            futureList.add(stringFuture)
        }
        latch.countDown()
        println("We had $threadOverlaps overlaps during the put method")
        assert(threadOverlaps.get() > 0)
        val stringSet = futureList.stream().map { f: Future<String?> ->
            try {
                return@map f.get()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
            null
        }.collect(Collectors.toSet())

//        System.out.println("Set size " + stringSet.size() + ". Set -> " + stringSet);
        if (stringSet.size == threads) {
            println("Put is Threadsafe")
        } else {
            println("Put is NOT Threadsafe")
        }
    }

    fun testRemove() {
        map["1"] = "Original"
        val futureList: MutableList<Future<String?>> = ArrayList()
        for (i in 0 until threads) {
            val value = values[i]
            val stringFuture = service.submit<String?> {
                latch.await()
                if (threadRunning.get()) { // One thread overlaps another
                    threadOverlaps.incrementAndGet()
                }
                threadRunning.set(true)
                Thread.sleep(50)
                val returnedValue = map.remove("1")
                threadRunning.set(false)
                returnedValue
            }
            futureList.add(stringFuture)
        }
        latch.countDown()
        println("We had $threadOverlaps overlaps during the remove method")
        assert(threadOverlaps.get() > 0)
        val stringList = futureList.stream().map { f: Future<String?> ->
            try {
                return@map f.get()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
            null
        }.collect(Collectors.toList())
        println("List size " + stringList.size + ". List -> " + stringList)
        if (Collections.frequency(stringList, "Original") == 1) {
            println("Remove is Threadsafe")
        } else {
            println("Remove is NOT Threadsafe")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val test = ThreadingTest()
            test.testPut()
            test.testRemove()
            System.exit(0)
        }
    }
}