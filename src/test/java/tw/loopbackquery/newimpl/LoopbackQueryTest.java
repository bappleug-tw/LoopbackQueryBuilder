package tw.loopbackquery.newimpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoopbackQueryTest {

    private ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Nested
    class OffsetTest {

        @Test
        void should_return_json_with_offset_when_offset_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .offset(1)
                    .build().toString();
            assertThat(json).isEqualTo("{\"offset\":1}");
        }

        @Test
        void should_return_json_without_offset_when_offset_not_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .build().toString();
            assertThat(json).isEqualTo("{}");
        }
    }

    @Nested
    class LimitTest {

        @Test
        void should_return_json_with_limit_when_limit_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .limit(1)
                    .build().toString();
            assertThat(json).isEqualTo("{\"limit\":1}");
        }

        @Test
        void should_return_json_without_limit_when_limit_not_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .build().toString();
            assertThat(json).isEqualTo("{}");
        }
    }

    @Nested
    class OrderTest {

        @Test
        void should_return_json_with_single_order_asc_when_single_order_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .order(Order.by("fieldName").asc())
                    .build().toString();
            assertThat(json).isEqualTo("{\"order\":\"fieldName asc\"}");
        }

        @Test
        void should_return_json_with_single_order_desc_when_single_order_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .order(Order.by("fieldName").desc())
                    .build().toString();
            assertThat(json).isEqualTo("{\"order\":\"fieldName desc\"}");
        }

        @Test
        void should_return_json_without_order_when_order_not_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .build().toString();
            assertThat(json).isEqualTo("{}");
        }
    }

    @Nested
    class IncludeTest {

        @Test
        void should_return_json_with_single_include_when_single_include_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .include("relation_name")
                    .build().toString();
            assertThat(json).isEqualTo("{\"include\":\"relation_name\"}");
        }

        @Test
        void should_return_json_with_no_include_when_include_not_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .build().toString();
            assertThat(json).isEqualTo("{}");
        }
    }

    @Nested
    class WhereTest {

        @Test
        void should_return_json_with_where_eq_string_when_where_eq_string_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .where(Where.by("fieldName").eq("strValue"))
                    .build().toString();
            assertThat(json).isEqualTo("{\"where\":{\"fieldName\":{\"eq\":\"strValue\"}}}");
        }

        @Test
        void should_return_json_with_no_include_when_include_not_set() {
            String json = LoopbackQuery.query(objectMapper)
                    .build().toString();
            assertThat(json).isEqualTo("{}");
        }
    }
}
