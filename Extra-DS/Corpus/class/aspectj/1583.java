/**
 * @testcase PR#715 PUREJAVA incrementing objects, arrays
 */
public class ArrayInc3CE {

    private static int[] IRA = new int[] { 0, 1, 2 };

    private static Object OBJECT = new Object();

    static int[] getIRA() {
        return IRA;
    }

    static Object getObject() {
        return null;
    }

    public void testObjectIncrementingCE() {
        int i = 0;
        Object object = new Object();
        String[] sra = new String[] { "" };
        // CE + not applied to int[], int
        IRA += 1;
        // CE + not applied to Object, int
        object += 1;
        // CE unary + not applied to int[]
        i = +IRA;
        // CE unary + not applied to int[]
        i = +getIRA();
        // CE string + not applied to String[], String
        sra += "bad concat";
    }
}
