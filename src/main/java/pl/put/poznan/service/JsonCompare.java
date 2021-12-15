package pl.put.poznan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import java.io.IOException;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JsonCompare {

    ObjectMapper mapper = new ObjectMapper();

    JsonNode json;

    List<String> firstTextDifference = new ArrayList<String>();
    List<String> secondTextDifference = new ArrayList<String>();

    public List<Integer> getStartEndIndexes(String text, int i) {

        List <Integer> indexes = new ArrayList<Integer>(2);

        for (int j = i; j > 0; j--) {
            if (text.charAt(j) == '\n') {
                indexes.add(j + 1);
                break;
            }
        }

        for (int j = i; j < text.length(); j++) {
            if (text.charAt(j) == '\n') {
                indexes.add(j + 1);
                break;
            }
        }

        return indexes;
    }

    public void addOutputLines(String firstText, String secondText) {

        int startOfLine = 0, endOfLine = 0;
        int firstLastDifferenceIndex = 0, secondLastDifferenceIndex = 0;
        boolean isCorrect = true;

        int i = 0, j = 0;

        while (i < firstText.length() && j < secondText.length()) {

            if (firstText.charAt(i) != secondText.charAt(j)) {
                isCorrect = false;
                startOfLine = getStartEndIndexes(firstText, i).get(0);
                endOfLine = getStartEndIndexes(firstText, i).get(1);

                i = endOfLine - 1;

                firstTextDifference.add(firstText.substring(firstLastDifferenceIndex, startOfLine));
                firstTextDifference.add("------------------------------------------------\n");
                firstTextDifference.add(firstText.substring(startOfLine, endOfLine));
                firstTextDifference.add("------------------------------------------------\n");

                firstLastDifferenceIndex = endOfLine;

                startOfLine = getStartEndIndexes(secondText, j).get(0);
                endOfLine = getStartEndIndexes(secondText, j).get(1);

                secondTextDifference.add(secondText.substring(secondLastDifferenceIndex, startOfLine));
                secondTextDifference.add("------------------------------------------------\n");
                secondTextDifference.add(secondText.substring(startOfLine, endOfLine));
                secondTextDifference.add("------------------------------------------------\n");

                secondLastDifferenceIndex = endOfLine;

                j = endOfLine - 1;
            }
            i++;
            j++;
        }

        if (!isCorrect) {
            firstTextDifference.add(firstText.substring(firstLastDifferenceIndex));
            secondTextDifference.add(secondText.substring(secondLastDifferenceIndex));
        } else {
            firstTextDifference.add("");
            secondTextDifference.add("");
        }
    }

    private List<JsonNode> jsonSplitter() throws IOException {

        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    private String outputToString() {

        StringBuilder firstSB = new StringBuilder();
        StringBuilder secondSB = new StringBuilder();

        int i = 0;
        while (i < firstTextDifference.size()) {
            firstSB.append(firstTextDifference.get(i));
            i++;
        }

        i = 0;
        while (i < secondTextDifference.size()) {
            secondSB.append(secondTextDifference.get(i));
            i++;
        }

        return firstSB.toString() + "\n" + secondSB.toString();
    }

    public String compare(String text) throws IOException {

        firstTextDifference.clear();
        secondTextDifference.clear();

        mapper = new ObjectMapper();
        json = mapper.readValue(text, JsonNode.class);
        String strJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

        strJson = strJson.substring(1, strJson.length() - 2);

        String firstText = strJson.substring(1, strJson.indexOf("}") + 1);
        String secondText = strJson.substring(strJson.indexOf("}") + 3);

        addOutputLines(firstText, secondText);

        List<JsonNode> jsons = jsonSplitter();

        EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
        JsonNode patch = JsonDiff.asJson(jsons.get(0), jsons.get(1), flags);
        String result = "JSONs:\n" + text + "\nDescription of the differences:\n" + patch.toString();

        return result + "\n" + outputToString();
    }
}
