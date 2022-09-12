import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public final class LoggingUtil {
    private static long startTime;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void logStartUp(String url) throws IOException {
        startTime = System.currentTimeMillis();
        WebhookBuilder.newBuilder(url)
                .content("[" + sdf.format(startTime) + "] client instance launching [host:" + InetAddress.getLocalHost().getHostName() + "]").buildAndExecute();
    }

    public static void logShutDown(String url) throws IOException {
        String s = String.format("%02dh:%02dm:%02ds",
                TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - startTime),
                TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - startTime) % 60,
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) % 60
        );
        WebhookBuilder.newBuilder(url)
                .content("[" + sdf.format(System.currentTimeMillis()) + "] client instance terminating [host:" + InetAddress.getLocalHost().getHostName()
                        + ", useTime:" + s + "]").buildAndExecute();
    }

    public static void log(String url, String content) {
        try {
            WebhookBuilder.newBuilder(url).content(content).buildAndExecute();
        }catch (IOException ignored) {}
    }
}
