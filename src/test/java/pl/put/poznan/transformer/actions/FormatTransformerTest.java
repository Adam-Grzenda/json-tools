package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

class FormatTransformerTest {

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
    void testFormat_Minify1() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\":\"xxx\",\n" +
                "  \"def\":\"yyy\",\n" +
                "  \"ghi\":\"zzz\"\n" +
                "}";
        expected =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";

        transformRequest.setJson(json);
        transformRequest.setMinify(true);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFormat_Minify2() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\":\"xxx\", \"def\" : \"yyy\",\n" +
                "  \n" +
                "\"ghi\":\"zzz\"}";
        expected =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";

        transformRequest.setJson(json);
        transformRequest.setMinify(true);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFormat_Deminify1() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        transformRequest.setJson(json);
        transformRequest.setDeminify(true);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFormat_Deminify2() throws JsonProcessingException {
        json =
                "{\"abc\"\n" +
                ":\"xxx\" , \"def\":\"yyy\" , \"ghi\":\"zzz\"}";
        expected =
                "{\n" +
                "  \"abc\" : \"xxx\",\n" +
                "  \"def\" : \"yyy\",\n" +
                "  \"ghi\" : \"zzz\"\n" +
                "}";

        transformRequest.setJson(json);
        transformRequest.setDeminify(true);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }

    @Test
    void testFormat_MinifyDeminify() throws JsonProcessingException {
        json =
                "{\"abc\":\"xxx\",\"def\":\"yyy\",\"ghi\":\"zzz\"}";
        expected =
                "{}";

        transformRequest.setJson(json);
        transformRequest.setMinify(true);
        transformRequest.setDeminify(true);

        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
        });
    }

    @Test
    void testFormat_NoChange() throws JsonProcessingException {
        json =
                "{\n" +
                "  \"abc\":\"xxx\", \"def\" : \"yyy\",\n" +
                "  \n" +
                "\"ghi\":\"zzz\"}";
        expected =
                "{\n" +
                "  \"abc\":\"xxx\", \"def\" : \"yyy\",\n" +
                "  \n" +
                "\"ghi\":\"zzz\"}";

        transformRequest.setJson(json);

        assertEquals(expected, transformerService.format(transformRequest).replaceAll("\\r\\n?", "\n"));
    }
}