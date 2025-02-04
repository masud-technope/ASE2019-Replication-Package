package pkg2;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareWarning;
import org.aspectj.lang.annotation.DeclareError;

public class InOneFile {

    public void hello() {
    }

    public void hi() {
    }

    public void target() {
        hello();
        hi();
    }

    @Aspect
    public static class DeowAspect {

        @DeclareWarning("call(* InOneFile.hello())")
        static final String onHello = "call hello";

        @DeclareError("call(* InOneFile.hi())")
        static final String onHi = "call hi";
    }
}
