package org.aspectj.apache.bcel.generic;

/** 
 * IFGT - Branch if int comparison with zero succeeds
 *
 * <PRE>Stack: ..., value -&gt; ...</PRE>
 *
 * @version $Id: IFGT.java,v 1.2 2004/11/19 16:45:19 aclement Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class IFGT extends IfInstruction {

    /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   */
     IFGT() {
    }

    public  IFGT(InstructionHandle target) {
        super(org.aspectj.apache.bcel.Constants.IFGT, target);
    }

    /**
   * @return negation of instruction
   */
    public IfInstruction negate() {
        return new IFLE(target);
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
        v.visitStackConsumer(this);
        v.visitBranchInstruction(this);
        v.visitIfInstruction(this);
        v.visitIFGT(this);
    }
}
