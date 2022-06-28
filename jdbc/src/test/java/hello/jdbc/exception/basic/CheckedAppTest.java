package hello.jdbc.exception.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public class CheckedAppTest {

    @Test
    void checked() throws SQLException, ConnectException {
        Controller controller=new Controller();
        Assertions.assertThatThrownBy(()->controller.request()).isInstanceOf(Exception.class);
    }
    static class Controller{
        Service service=new Service();
        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }
    static class Service{
       Repository repository=new Repository();
       NetWorkClient netWorkClient=new NetWorkClient();
       public void logic() throws ConnectException, SQLException {
           repository.call();
           netWorkClient.call();
       }
    }

    static class NetWorkClient{
       public void call() throws ConnectException {
           throw new ConnectException("ex");
       }
    }

    static class Repository{
        public void call() throws SQLException {
            throw new SQLException("ex");
        }
    }
}
