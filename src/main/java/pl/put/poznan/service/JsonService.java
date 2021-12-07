package pl.put.poznan.service;

import org.springframework.stereotype.Service;
import pl.put.poznan.transform.JsonTransformer;
import pl.put.poznan.transform.impl.AddBraces;
import pl.put.poznan.transform.impl.JsonTransformerImpl;
import pl.put.poznan.transform.impl.RemoveWhitespace;

@Service
public class JsonService {

    JsonTransformer withoutWhitespace = new RemoveWhitespace(new JsonTransformerImpl());

    public String transform(String text){
        return text.toUpperCase();
    }

    public String addBracesAndRemoveWhitespace(String text) {
        JsonTransformer jsonTransformer = new AddBraces(withoutWhitespace);

        JsonTransformer jsonTransformer1 =  new AddBraces(new AddBraces(new JsonTransformerImpl()));
        return jsonTransformer1.transform(text);
    }

}
