package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {
    /**
     * RuntimeException을 상속받은 예외는 언체크 예외
     */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message){
            super(message);
        }

    }

    @Test
    void checked_catch() {
        Service service = new Service();
        service.catchCall();
    }

    @Test
    void checked_throw(){
        Service service=new Service();
        Assertions.assertThatThrownBy(()->service.callThrow()).isInstanceOf(MyUncheckedException.class);
    }

    /**
     * Unchecked 예외는 예외를 잡거나, 예외를 잡지 않으면 자동으로 밖으로 던져짐
     */
    static class Service{
        Repository repository=new Repository();
        public void catchCall(){
            try {
                repository.call();
            }catch (MyUncheckedException e){
                log.info("예외 처리, message={}",e.getMessage(),e);
            }
        }

        public void callThrow(){
            repository.call();
        }

    }

    static class Repository{
        public void call(){
            throw new MyUncheckedException("ex");
        }
    }
}
