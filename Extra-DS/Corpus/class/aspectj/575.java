import org.aspectj.lang.annotation.*;

interface A {

    @Before("call(* *.*(..))")
    void someCall();
}
