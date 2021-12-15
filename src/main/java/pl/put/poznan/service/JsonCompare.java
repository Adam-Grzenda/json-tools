package pl.put.poznan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import java.io.IOException;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JsonCompare {

    ObjectMapper mapper;

    JsonNode json;

    public List<List<String>> addOutputLines(String firstText, String secondText) {

        String[] firstTextLines = firstText.split("\n");
        String[] secondTextLines = secondText.split("\n");

        List<List<String>> result = new ArrayList<List<String>>(2);

        List<String> firstTextDifference = new ArrayList<String>();
        List<String> secondTextDifference = new ArrayList<String>();

        boolean isCorrect = true;

        int lastModifiedLineFirst = 0, lastModifiedLineSecond = 0, i = 0, j = 0;

        while (i < firstTextLines.length && j < secondTextLines.length) {

            if (!firstTextLines[i].equals(secondTextLines[j])) {
                isCorrect = false;

                for (int x = lastModifiedLineFirst; x < i;  x++) {
                    firstTextDifference.add(firstTextLines[x]);
                }
                firstTextDifference.add("------------------------------------------------\n");
                firstTextDifference.add(firstTextLines[i]);
                firstTextDifference.add("------------------------------------------------\n");

                lastModifiedLineFirst = i + 1;

                for (int x = lastModifiedLineSecond; x < j;  x++) {
                    secondTextDifference.add(secondTextLines[x]);
                }
                secondTextDifference.add("------------------------------------------------\n");
                secondTextDifference.add(secondTextLines[j]);
                secondTextDifference.add("------------------------------------------------\n");

                lastModifiedLineSecond = j + 1;
            }
            i++;
            j++;
        }

        if (!isCorrect) {

            for (int x = lastModifiedLineFirst; x < firstTextLines.length;  x++) {
                firstTextDifference.add(firstTextLines[x]);
            }

            for (int x = lastModifiedLineSecond; x < secondTextLines.length;  x++) {
                secondTextDifference.add(secondTextLines[x]);
            }
        } else {
            firstTextDifference.add("");
            secondTextDifference.add("");
        }

        result.add(firstTextDifference);
        result.add(secondTextDifference);

        return result;
    }

    private List<JsonNode> jsonSplitter() throws IOException {

        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    public String compare(String text) throws IOException {

        mapper = new ObjectMapper();
        json = mapper.readValue(text, JsonNode.class);
        String strJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

        strJson = strJson.substring(1, strJson.length() - 2);

        String firstText = strJson.substring(1, strJson.indexOf("}") + 1);
        String secondText = strJson.substring(strJson.indexOf("}") + 3);

        List<List<String>> result = addOutputLines(firstText, secondText);
        List<String> firstTextDifference = result.get(0);
        List<String> secondTextDifference = result.get(1);

        List<JsonNode> jsons = jsonSplitter();

        EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
        JsonNode patch = JsonDiff.asJson(jsons.get(0), jsons.get(1), flags);
        String output = "JSONs:\n" + text + "\nDescription of the differences:\n" + patch.toString();

        return output + "\n" + String.join("", firstTextDifference)
                + "\n" + String.join("", secondTextDifference);
    }
}
