package org.aspectj.apache.bcel.generic;

/** 
 * ISUB - Substract ints
 * <PRE>Stack: ..., value1, value2 -&gt; result</PRE>
 *
 * @version $Id: ISUB.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ISUB extends ArithmeticInstruction {

    /** Substract ints
   */
    public  ISUB() {
        super(org.aspectj.apache.bcel.Constants.ISUB);
    }

    /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * @param v Visitor object
   */
    public void accept(Visitor v) {
        v.visitTypedInstruction(this);
        v.visitStackProducer(this);
        v.visitStackConsumer(this);
        v.visitArithmeticInstruction(this);
        v.visitISUB(this);
    }
}
