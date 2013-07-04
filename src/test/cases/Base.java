import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author khotyn 2013-07-03 10:18:08
 */
public class Base {

    private static final String EXPECTED_RESULT          = "one";
    private static final String EXPECTED_RELOADED_RESULT = "two";

    public static void main(String[] args) throws Exception {
        String caseName = args[0];
        Class<?> klass = Thread.currentThread().getContextClassLoader().loadClass("A");
        Method method = klass.getMethod("test");
        String result = (String) method.invoke(klass.newInstance());
        TimeUnit.SECONDS.sleep(2);
        String reloadedResult = (String) method.invoke(klass.newInstance());

        if (!EXPECTED_RESULT.equals(result) || !EXPECTED_RELOADED_RESULT.equals(reloadedResult)) {
            System.err.println(caseName + " failed:");
            System.err.println("Expected result before reloaded is " + EXPECTED_RESULT + ", but got "
                               + (result == null ? "null" : result));
            System.err.println("Expected result after reloaded is " + EXPECTED_RELOADED_RESULT + ", but got "
                               + (reloadedResult == null ? "null" : reloadedResult));
            System.exit(1);
        }

        System.out.println(caseName + " success!");
    }
}
