package com.hazelcast.sql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.impl.SqlTestSupport;
import com.hazelcast.sql.dto.Person;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.hazelcast.config.IndexType.SORTED;

public class SqlIntegrationTest extends SqlTestSupport {

    public static final String SQL_TEST_DATA_FILE = "src/test/resources/sql_test_data.json";
    private static final SqlTestInstanceFactory FACTORY = SqlTestInstanceFactory.create();
    private static final String MAP_OBJECT = "map_object";
    private static final List<Boolean> ASC_ORDER = Arrays.asList(false);
    private static final List<Boolean> DESC_ORDER = Arrays.asList(true);
    private static HazelcastInstance member;


    @BeforeClass
    public static void setup() {
        member = FACTORY.newHazelcastInstance(SqlBasicTest.memberConfig());
        populateDB();
    }

    @Test
    public void testNestedSelectWithOrderAndNotLike() {

        addIndexedFields(Arrays.asList("age"));

        SqlResult sqlResult = member.getSql().execute("SELECT * FROM " +
                "(SELECT id,age,company,favoriteFruit FROM " + MAP_OBJECT +
                " WHERE favoriteFruit NOT LIKE '%berry' " + "ORDER BY age ASC) " +
                "WHERE age < 35 AND age > 30 ORDER BY age DESC OFFSET 5 ROWS FETCH FIRST 10 ROWS ONLY");

        SqlRowMetadata rowMetadata = sqlResult.getRowMetadata();
        Iterator<SqlRow> rowIterator = sqlResult.iterator();
        printSqlResult(rowMetadata, rowIterator);
        SqlRow previousRow = null;
        int count = 0;

        while (rowIterator.hasNext()) {
            SqlRow currentRow = rowIterator.next();
            assertOrdered(previousRow, currentRow, orderFields("age"), DESC_ORDER, rowMetadata);
            Assert.assertFalse(currentRow.getObject("favoriteFruit").toString().contains("berry"));
            previousRow = currentRow;
            count++;
        }

        Assert.assertTrue(count <= 10);
    }

    @Test
    public void testOrderByDate() {
        addIndexedFields(Arrays.asList("registered"));

        SqlResult sqlResult = member.getSql().execute("SELECT * FROM " + MAP_OBJECT +
                " WHERE age < 35 AND age > 30 ORDER BY registered ASC FETCH FIRST 1000 ROWS ONLY");

        SqlRowMetadata rowMetadata = sqlResult.getRowMetadata();
        Iterator<SqlRow> rowIterator = sqlResult.iterator();
        printSqlResult(rowMetadata, rowIterator);
        SqlRow previousRow = null;
        int count = 0;

        while (rowIterator.hasNext()) {
            SqlRow currentRow = rowIterator.next();
            assertOrdered(previousRow, currentRow, orderFields("registered"), DESC_ORDER, rowMetadata);
            previousRow = currentRow;
            count++;
        }

        Assert.assertTrue(count <= 10);
    }

    static void populateDB() {
        IMap<Integer, Person> map = member.getMap(MAP_OBJECT);
        List<Person> personList = getPersonData();
        for (int x=0; x<personList.size(); x++) {
            map.put(x, personList.get(x));
        }
    }

    static void addIndexedFields(List<String> indexedFields) {
        IndexConfig indexConfig = new IndexConfig().setName("Index_" + randomName())
                .setType(SORTED);
        for(String field : indexedFields) {
            indexConfig.addAttribute(field);
        }

        member.getMap(MAP_OBJECT).addIndex(indexConfig);
    }

    static List<Person> getPersonData() {
        ObjectMapper mapper = new ObjectMapper();
        List<Person> values = null;
        try {
            values = mapper.readValue(new File(SQL_TEST_DATA_FILE), new TypeReference<List<Person>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull("Failed to parse test data file", values);
        return values;
    }

    static List<String> orderFields(String... fields) {
        return Arrays.asList(fields);
    }

}
