package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

class SortTransformerTest {

    private TransformerService transformerService;
    private TransformRequest transformRequest;
    private String json;
    private String expected;

    @BeforeEach
    void setUp() {
        transformerService = new TransformerService();
        transformRequest = new TransformRequest();
    }

    @Test
    void testSort_Minified() throws JsonProcessingException {
        json =
                "{\"def\":\"yyy\",\"abc\":\"xxx\",\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.sort(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testSort_Deminified() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.sort(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testSort_AlreadySorted() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.sort(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testSort_MoreValues() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"def\" : \"jkl\",\n" +
                "  \"abc\" : \"ghi\",\n" +
                "  \"ghi\" : \"abc\",\n" +
                "  \"mno\" : \"pqr\",\n" +
                "  \"jkl\" : \"def\",\n" +
                "  \"pqr\" : \"mno\"\n" +
                "}";
        expected =
                "{\n" +
                "  \"abc\" : \"ghi\",\n" +
                "  \"def\" : \"jkl\",\n" +
                "  \"ghi\" : \"abc\",\n" +
                "  \"jkl\" : \"def\",\n" +
                "  \"mno\" : \"pqr\",\n" +
                "  \"pqr\" : \"mno\"\n" +
                "}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.sort(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testSort_EmptyJson() throws JsonProcessingException {
        json =
                "{}";
        expected =
                "{}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.sort(transformRequest).replaceAll("\\r\\n?", "\n"));
    }
}