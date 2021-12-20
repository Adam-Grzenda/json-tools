package pl.put.poznan.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.mapper.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CompareService {

    private final JsonMapper mapper = JsonMapper.getInstance();

    public CompareService() {
        log.info("Initialized CompareService");
    }

    public List<List<String>> addOutputLines(String firstText, String secondText) {

        String[] firstTextLines = firstText.split("\n");
        String[] secondTextLines = secondText.split("\n");

        List<List<String>> result = new ArrayList<>(2);

        List<String> firstTextDifference = new ArrayList<>();
        List<String> secondTextDifference = new ArrayList<>();

        boolean isCorrect = true;

        int lastModifiedLineFirst = 0, lastModifiedLineSecond = 0, i = 0, j = 0;

        while (i < firstTextLines.length && j < secondTextLines.length) {

            if (!firstTextLines[i].equals(secondTextLines[j])) {
                isCorrect = false;

                firstTextDifference.addAll(Arrays.asList(firstTextLines)
                        .subList(lastModifiedLineFirst, i));
                firstTextDifference.add(firstTextLines[i] + " <--- Mismatch");

                lastModifiedLineFirst = i + 1;

                secondTextDifference.addAll(Arrays.asList(secondTextLines)
                        .subList(lastModifiedLineSecond, j));
                secondTextDifference.add(secondTextLines[j] + " <--- Mismatch");

                lastModifiedLineSecond = j + 1;
            }
            i++;
            j++;
        }

        if (!isCorrect) {
            firstTextDifference.addAll(Arrays.asList(firstTextLines)
                    .subList(lastModifiedLineFirst, firstTextLines.length));
            secondTextDifference.addAll(Arrays.asList(secondTextLines)
                    .subList(lastModifiedLineSecond, secondTextLines.length));
        }

        result.add(firstTextDifference);
        result.add(secondTextDifference);

        return result;
    }

    private List<JsonNode> jsonSplitter(JsonNode json) {
        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    public String compare(String text) throws IOException {

        JsonNode json = mapper.readJson(text, JsonNode.class);
        String strJson = mapper.writeJsonAsString(json, true);

        strJson = strJson.substring(1, strJson.length() - 2);

        String firstText = strJson.substring(1, strJson.indexOf("}") + 1);
        String secondText = strJson.substring(strJson.indexOf("}") + 3);

        log.debug("First compared text: " + firstText);
        log.debug("Second compared text: " + secondText);

        List<List<String>> result = addOutputLines(firstText, secondText);
        List<String> firstTextDifference = result.get(0);
        List<String> secondTextDifference = result.get(1);

        List<JsonNode> jsons = jsonSplitter(json);

        EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
        JsonNode patch = JsonDiff.asJson(jsons.get(0), jsons.get(1), flags);
        String output = patch.toString() + "\n" + String.join("\n", firstTextDifference) + "\n" + String.join("\n", secondTextDifference);

        log.debug("Compare service output: " + output);
        return output;
    }
}
