import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Downloader {
//
//    public static void main(String[] args) throws InterruptedException {
//        File file = download(new String[] {"http://mirror1.com/file",
//                "http://mirror2.com/file", "http://mirror3.com/file",
//                "http://mirror4.com/file"});
//        System.out.println(file.getPath());
//    }
//
//    private static File download(String[] urls) throws InterruptedException {
//
//        ExecutorService service = Executors.newFixedThreadPool(urls.length);
//
//        List<Callable<File>> callableList = new ArrayList();
//        for (final String url: urls) {
//
//            callableList.add(new Callable<File>(){
//                public File call() throws Exception {
//                    return download(url);
//                }
//            });
//        }
//
//        List<Future<File>> futures = service.invokeAll(callableList);
//
////        futures.stream().filter(x -> true).forEach(future -> future.cancel(true));
//
//        return null;
//    }
//    private static File download(String url) {
//        File file = new File(url);
//        int delay = (int) ((1+Math.random())*1000);
//        System.out.println(url+" - "+delay);
//        try {
//            Thread.sleep(delay);
//        } catch (InterruptedException e) {}
//        return file;
//    }

}