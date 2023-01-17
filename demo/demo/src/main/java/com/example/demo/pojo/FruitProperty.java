package com.example.demo.pojo;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("fruit")
//@Data //Lombok 설정, 컴파일 시점에 getter, setter를 클래스 내부 필드에 자동 생성
//@Component //사용하려는 곳에서 의존성 주입
//@ConfigurationProperties("fruit") //프로퍼티 키 값을 읽어와서 필드값에 매핑함
public class FruitProperty {
    //Fruit POJO 방식 사용 : List<Map> 방식보다 더 직관적이고 명확함
    private List<Fruit> list;

//    List<Map> 방식 사용
//    private List<Map> list;
//

}
