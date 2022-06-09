package com.epam.tkach.carrent.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class needed for creation queries with dynamic filtration and limits
 */
public class QueryBuilder {
    /**
     *
     * @param Query - simple select query without where, order by, limit operators
     * @param filtersMap - Linked hash map. key - name of column in query, value - parameter
     * @param orderBy - field that will be used to sort table
     * @return
     */
    public static String buildQuery(String Query, LinkedHashMap<String, Object> filtersMap, String orderBy, boolean addLimit) {
        StringBuilder builder = new StringBuilder();
        builder.append(Query);
        if (filtersMap!=null&&!filtersMap.isEmpty()){
            builder.append(" where ");
            boolean first = true;
            for (Map.Entry<String, Object> entry : filtersMap.entrySet()) {
                String key = entry.getKey();
                if (!first) builder.append(" and ");
                builder.append(key);
                builder.append("=?");
                first = false;
            }
        }
        if (orderBy!=null&&!orderBy.isEmpty()) {
            //add order by
            builder.append(" order by ");
            builder.append(orderBy);
        }

        if (addLimit){
        //add limit
            builder.append(" limit ?,?");
        }
        return builder.toString();
    }

    public static void setParamsToPreparedStatement(PreparedStatement pstmt, boolean setLimit,  int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filtersMap) throws SQLException {
        int i = 1;
        if (filtersMap != null && !filtersMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : filtersMap.entrySet()) {
                Object value = entry.getValue();
                pstmt.setObject(i++, value);
            }
        }
        //Setting limit params
        if (setLimit){
            pstmt.setInt(i++, (currentPage - 1) * recordsPerPage);
            pstmt.setInt(i,recordsPerPage);
        }
    }
}
