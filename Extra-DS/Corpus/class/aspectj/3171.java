import org.aspectj.testing.Tester;

public class LenientTest {

    public void m() {
        // CE 6 in -lenient only
        return;
        // CE 6 in -lenient only
        ;
    }

    public static void main(String[] args) {
        Tester.check(null != new LenientTest(), "no test");
    }
}
