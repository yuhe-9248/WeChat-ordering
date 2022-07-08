package com.imooc;



import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yuhe
 * @date 2021/11/14 19:45
 */

//@RunWith(SpringRunner.class)
@SpringBootTest
//@Slf4j
public class LoggerTest {

    private static final Logger log = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){

        String name = "imooc";
        String password = "123456";

        log.debug("debug...");
        log.info("info...");
        log.info("name:{},password:{}",name,password);
        log.error("error...");
    }
}
