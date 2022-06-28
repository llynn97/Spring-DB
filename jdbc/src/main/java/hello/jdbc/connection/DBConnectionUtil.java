package hello.jdbc.connection;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection()  {

        try{
            //커넥션 구현체 반환(H2 드라이버가 제공하는 H2커넥션)
            Connection connection=DriverManager.getConnection(ConnectionConst.URL,ConnectionConst.USERNAME,ConnectionConst.PASSWORD);
            log.info("get Connection={} ,class={}",connection,connection.getClass());
            return connection;

        }catch (SQLException e){
            throw  new IllegalStateException(e);
        }



    }
}
