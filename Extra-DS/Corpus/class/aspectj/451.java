/**
 * @testcase PR#715 PUREJAVA incrementing objects, arrays
 */
public class ArrayInc2CE {

    public void testObjectIncrementingCE() {
        boolean b;
        int i = 0;
        Object object = new Object();
        int[] ra = new int[] {};
        // CE prefix ++ cannot be applied to int[]
        ++ra;
        // CE prefix ++ cannot be applied to Object
        ++object;
        // CE postfix ++ cannot be applied to int[]
        ra++;
        // CE postfix ++ cannot be applied to Object
        object++;
    }
}
