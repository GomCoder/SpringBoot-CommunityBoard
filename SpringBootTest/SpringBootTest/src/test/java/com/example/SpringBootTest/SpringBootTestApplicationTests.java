package com.example.SpringBootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//@RunWith(SpringRunner.class)
//@SpringBootTest(value="value=test")
//public class SpringBootTestApplicationTests {
//	@Value("${value}")
//	private String value;
//
//	@Test
//	public void contextLoads() {
//		assertThat(value, is("test"));
//	}
//}


@RunWith(SpringRunner.class)
@SpringBootTest(properties={"property.value=propertyTest"}, classes={SpringBootTestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestApplicationTests {
	@Value("${property.value}")
	private String propertyValue;

	@Test
	public void contextLoads() {
		assertThat(propertyValue, is("propertyTest"));
	}
}