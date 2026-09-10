
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(100, "0");
        map.put(101, "0");
        map.put(102, "1");
        map.put(103, "0");
        map.put(104, "0");

        map.put(200, "1");
        map.put(201, "1");
        map.put(202, "1");
        map.put(203, "1");
        map.put(204, "1");

        
        System.out.println(map);

    }
}
