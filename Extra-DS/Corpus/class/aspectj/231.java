import org.aspectj.lang.annotation.*;

@Aspect
class A {

    @Pointcut("call(* *.*(..))")
    void someCall() {
        System.out.println("whoops");
    }
}
