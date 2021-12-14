package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private List<JsonNode> jsonSplitter() throws IOException {

        return StreamSupport.stream(json.spliterator(), false).collect(Collectors.toList());
    }

    public String compare(String text) throws JsonProcessingException {

        try {
            json = mapper.readValue(text, JsonNode.class);
            List<JsonNode> jsons = jsonSplitter();

            EnumSet<DiffFlags> flags = DiffFlags.dontNormalizeOpIntoMoveAndCopy().clone();
            JsonNode patch = JsonDiff.asJson(jsons.get(0), jsons.get(1), flags);
            String result = "JSONs:\n" + text + "\nDescription of the differences:\n" + patch.toString();

            return result;
        } catch (IOException e){
            e.printStackTrace();
        }

        return "";
    }
}
