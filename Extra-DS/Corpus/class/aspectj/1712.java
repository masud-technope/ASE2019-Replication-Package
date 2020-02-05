package org.aspectj.apache.bcel.generic;

/**
 * NOP - Do nothing
 *
 * @version $Id: NOP.java,v 1.2 2004/11/19 16:45:18 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class NOP extends Instruction {

    public  NOP() {
        super(org.aspectj.apache.bcel.Constants.NOP, (short) 1);
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
        v.visitNOP(this);
    }
}
