import org.aspectj.lang.annotation.*;

interface A {

    @Pointcut("call(* *.*(..))")
    void someCall();
}
