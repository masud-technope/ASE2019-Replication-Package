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

import org.aspectj.weaver.ResolvedType;

public class Var extends Expr {

    ResolvedType type;

    public  Var(ResolvedType type) {
        super();
        this.type = type;
    }

    public ResolvedType getType() {
        return type;
    }

    public String toString() {
        return "(Var " + type + ")";
    }

    public void accept(IExprVisitor v) {
        v.visit(this);
    }
}
