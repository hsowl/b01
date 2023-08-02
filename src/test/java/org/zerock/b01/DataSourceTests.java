package org.zerock.b01;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
@Log4j2
public class DataSourceTests {

    @Autowired // 이걸없애면 null값이기 때문에 실패
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception{

        @Cleanup // 자동으로 close
        Connection con = dataSource.getConnection();
        log.info("con=======>" + con);
        Assertions.assertNotNull(con); // not null값이면 성공

    }
}
