import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int optionCount = Integer.parseInt(reader.readLine());
        List<String> optionList = new ArrayList<>();
        for (int i = 0; i < optionCount; i++) {
            optionList.add(reader.readLine());
        }

        // 단축키 지정
        Set<Character> shortcutSet = new TreeSet<>();
        for (int optionsListIndex = 0; optionsListIndex < optionList.size(); optionsListIndex++) {
            String option = optionList.get(optionsListIndex);
            boolean shortcutAssigned = false;

            String[] optionWords = option.split(" ");
            StringBuilder newOption = new StringBuilder();
            for (String word : optionWords) {
                char candidate = Character.toLowerCase(word.charAt(0));

                if (!shortcutSet.contains(candidate) && !shortcutAssigned) {
                    shortcutSet.add(candidate);
                    newOption.append("[" + word.charAt(0) + "]" + word.substring(1));
                    shortcutAssigned = true;
                } else {
                    newOption.append(word);
                }
                newOption.append(" ");
            }

            if (shortcutAssigned) {
                optionList.set(optionsListIndex, newOption.toString().stripTrailing());
                continue;
            }

            int optionIndex = 0;
            while (optionIndex < option.length()) {
                char candidate = Character.toLowerCase(option.charAt(optionIndex));
                if (Character.isAlphabetic(candidate) && !shortcutSet.contains(candidate)) {
                    shortcutSet.add(candidate);
                    break;
                }
                optionIndex++;
            }

            String newOptionString = option;
            if (optionIndex < option.length()) {
                newOptionString =
                        option.substring(
                                0, optionIndex) + "[" + option.charAt(optionIndex) + "]" + option.substring(
                                optionIndex + 1);
            }
            optionList.set(optionsListIndex, newOptionString);
        }

        // 출력
        optionList.forEach(System.out::println);

        // 종료
        reader.close();
    }
}
