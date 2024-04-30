package egovframework.example.cmmn;

public class Utils {
    public static boolean isNotNull(int val) { return val > 0; }
    public static boolean isNotNull(String str) { return str != null; }
    public static boolean isNotNull(Object obj) { return obj != null; }
    public static boolean isNull(int val) { return val == 0; }
    public static boolean isNull(String str) { return str == null; }
    public static boolean isNull(Object obj) { return obj == null; }
}
