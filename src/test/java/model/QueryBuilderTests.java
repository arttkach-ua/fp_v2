package model;

import com.epam.tkach.carrent.model.QueryBuilder;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryBuilderTests {
    @Test
    public void buildSelectWithFiltersLimitsSort(){
        String expected = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars where graduation_year=? and brand_id=? order by id limit ?,?";
        String query = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars";
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("graduation_year", 2019);
        map.put("brand_id", 1);
        String resultQuery = QueryBuilder.buildQuery(query,map,"id", true);
        System.out.println(resultQuery);
        assertEquals(expected, resultQuery);
    }

    @Test
    public void buildSelectWithNullFiltersNoSort(){
        String expected = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars limit ?,?";
        String query = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars";
        String resultQuery = QueryBuilder.buildQuery(query,null,"", true);
        System.out.println(resultQuery);
        assertEquals(expected, resultQuery);
    }

    @Test
    public void buildSelectWithEmptyFiltersNoSort(){
        String expected = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars limit ?,?";
        String query = "select id, graduation_year, model_id, brand_id, vin_code, state_number, tariff_id, complete_set_id from cars";
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        String resultQuery = QueryBuilder.buildQuery(query,map,null, true);
        System.out.println(resultQuery);
        assertEquals(expected, resultQuery);
    }

}
