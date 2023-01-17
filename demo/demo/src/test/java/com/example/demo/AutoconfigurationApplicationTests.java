package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoconfigurationApplicationTests {

    //@Value의 매핑방식
    //    @Value("${property.test.name}"): 깊이가 존재하는 키 값에 대해 '.'로 구분하여 해당 값을 매핑함
    //    @Value("${propertyTest}"): 단일 키값을 매핑함
    //    @Value("${noKey:default value}"): YAML 파일에 키 값이 존재하지 않으면 Default 값이 매칭 되도록 설정함
    //    @Value("${propertyTestList}"): 여러 값을 나열할 때는 배열형으로 매핑함
    //    @Value("#{'${propertyTestList}'.split(',')}"): SqEL을 사용하여 ','를 기준으로 List에 매핑함

    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest;

    @Value("${noKey:default value}")
    private String defaultValue;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue() {
        assertThat(propertyTestName, is("property depth test"));
        assertThat(propertyTest, is("test"));
        assertThat(defaultValue, is("default value"));

        assertThat(propertyTestArray[0], is("a"));
        assertThat(propertyTestArray[1], is("b"));
        assertThat(propertyTestArray[2], is("c"));

        assertThat(propertyTestList.get(0), is("a"));
        assertThat(propertyTestList.get(1), is("b"));
        assertThat(propertyTestList.get(2), is("c"));
    }
}
