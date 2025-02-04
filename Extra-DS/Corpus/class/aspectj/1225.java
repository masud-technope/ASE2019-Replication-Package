/* *******************************************************************
 * Copyright (c) 2002 Palo Alto Research Center, Incorporated (PARC).
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     PARC     initial implementation 
 * ******************************************************************/
package org.aspectj.weaver.ast;

import org.aspectj.weaver.Member;

public class FieldGetCall extends Test {

    // assert m.return value is boolean
    private final Member field;

    private final Member method;

    private final Expr[] args;

    public  FieldGetCall(Member f, Member m, Expr[] args) {
        super();
        this.field = f;
        this.method = m;
        this.args = args;
    }

    public void accept(ITestVisitor v) {
        v.visit(this);
    }

    public Expr[] getArgs() {
        return args;
    }

    public Member getMethod() {
        return method;
    }

    public Member getField() {
        return field;
    }
}
