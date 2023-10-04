import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Node root = new Node("");

        int inputSize = Integer.parseInt(reader.readLine());
        for (int i = 0; i < inputSize; i++) {
            // 경로 입력
            String input = reader.readLine();

            // 역슬래쉬(\) 단위로 분할
            String[] path = input.split("\\\\");

            // Map에 삽입
            insert(root, path);
        }

        // 결과 출력
        for (Node sub : root.sub.values()) {
            print(sub, "");
        }

        reader.close();
    }

    private static void insert(Node root, String[] path) {
        Node current = root;
        for (String dir : path) {
            current.sub.putIfAbsent(dir, new Node(dir));
            current = current.sub.get(dir);
        }
    }

    private static void print(Node dir, String prefix) {
        System.out.println(prefix + dir.name);

        for (Node sub : dir.sub.values()) {
            print(sub, prefix + " ");
        }
    }

    private static class Node {
        String name;
        Map<String, Node> sub = new TreeMap<>();

        public Node(String name) {
            this.name = name;
        }
    }
}
