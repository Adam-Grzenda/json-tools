package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

import java.util.ArrayList;
import java.util.List;

class FilterTransformerTest {

    private TransformerService transformerService;
    private TransformRequest transformRequest;
    private String json;
    private String expected;
    private List<String> includeFields;
    private List<String> excludeFields;

    @BeforeEach
    void setUp() {
        transformerService = new TransformerService();
        transformRequest = new TransformRequest();
        includeFields = new ArrayList<>();
        excludeFields = new ArrayList<>();
    }

    @Test
    void testFilter_Include1() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\"def\":\"yyy\"}";

        includeFields.add("def");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_Include2() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"def\" : \"yyy\"\n" +
                "}";

        includeFields.add("def");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_Exclude1() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\",\"ghi\":\"zzz\"}";

        excludeFields.add("def");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_Exclude2() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        excludeFields.add("def");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_IncludeExclude() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{}";

        includeFields.add("abc");
        excludeFields.add("def");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
        });
    }

    @Test
    void testFilter_NoChange() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_NullLists() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(null);
        transformRequest.setExcludeFields(null);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_EmptyFields() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\"\n" +
                "}";

        includeFields.add("abc");
        includeFields.add("def");
        includeFields.add("");
        excludeFields.add("");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_DuplicateInclude() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\",\"ghi\":\"zzz\"}";

        includeFields.add("abc");
        includeFields.add("abc");
        includeFields.add("ghi");
        includeFields.add("abc");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFilter_DuplicateExclude() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\"}";

        excludeFields.add("def");
        excludeFields.add("ghi");
        excludeFields.add("ghi");

        transformRequest.setJson(json);
        transformRequest.setIncludeFields(includeFields);
        transformRequest.setExcludeFields(excludeFields);

        assertEquals(expected, transformerService.filter(transformRequest).replaceAll("\\r\\n?", "\n"));
    }
}