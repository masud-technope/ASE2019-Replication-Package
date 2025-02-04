public class Driver {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        // integer literals
        int dec = 5;
        long longDec = 5;
        long longDecL = 5L;
        int hex = 0xAbcdE;
        long longHex = 0xAbcdE;
        long longHexL = 0xAbcdEL;
        int oct = 0762;
        long longOct = 0762;
        long longOctL = 0762L;
        // boolean literals
        boolean btrue = true;
        boolean bfalse = false;
        // float literals
        float f1 = 1e1f, f2 = 2.f, f3 = .3f, f4 = 3.14f, f5 = 6.023e+23f;
        // character literals
        char // c1 = '\u2352', 
        c2 = // 'c'
        'c';
        //      c3 = '\u0007';
        // string literals
        // String c = "c";
        String c = "c";
        String s1 = "";
        // the string "c";
        String s2 = "c";
        //    String s3 = "\u3333"; // uncommenting this will break weaver
        // string literals with escapes
        String bs = "\b";
        String ht = "\t";
        String lf = "\n";
        String cr = "\r";
        String dq = "\"";
        String sq = "\'";
        String backslash = "\\";
        String oes = " ";
        String oeb = "�";
        String ctrlg = "";
        String random = "?";
    }
}
