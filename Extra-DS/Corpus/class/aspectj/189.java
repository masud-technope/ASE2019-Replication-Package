/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.aspectj.org.eclipse.jdt.core.dom;

import java.util.ArrayList;
import java.util.List;
import org.aspectj.org.eclipse.jdt.core.dom.AST;
import org.aspectj.org.eclipse.jdt.core.dom.ASTNode;

/**
 * OrPointcut DOM AST node.
 * has:
 *   everything PointcutDesignators have
 *   a PointcutDesignator called 'left'
 *   a PointcutDesignator called 'right'
 *   
 * @author ajh02
 */
public class OrPointcut extends PointcutDesignator {

    private PointcutDesignator left = null;

    public static final ChildPropertyDescriptor LEFT_PROPERTY = //$NON-NLS-1$
    new ChildPropertyDescriptor(OrPointcut.class, "left", PointcutDesignator.class, MANDATORY, NO_CYCLE_RISK);

    public PointcutDesignator getLeft() {
        return this.left;
    }

    public void setLeft(PointcutDesignator left) {
        if (left == null) {
            throw new IllegalArgumentException();
        }
        ASTNode oldChild = this.left;
        preReplaceChild(oldChild, left, LEFT_PROPERTY);
        this.left = left;
        postReplaceChild(oldChild, left, LEFT_PROPERTY);
    }

    private PointcutDesignator right = null;

    public static final ChildPropertyDescriptor RIGHT_PROPERTY = //$NON-NLS-1$
    new ChildPropertyDescriptor(OrPointcut.class, "right", PointcutDesignator.class, MANDATORY, NO_CYCLE_RISK);

    public PointcutDesignator getRight() {
        return this.right;
    }

    public void setRight(PointcutDesignator right) {
        if (right == null) {
            throw new IllegalArgumentException();
        }
        ASTNode oldChild = this.right;
        preReplaceChild(oldChild, right, RIGHT_PROPERTY);
        this.right = right;
        postReplaceChild(oldChild, right, RIGHT_PROPERTY);
    }

     OrPointcut(AST ast) {
        super(ast);
    }

    public static List propertyDescriptors(int apiLevel) {
        List propertyList = new ArrayList(2);
        createPropertyList(ReferencePointcut.class, propertyList);
        addProperty(LEFT_PROPERTY, propertyList);
        addProperty(RIGHT_PROPERTY, propertyList);
        return reapPropertyList(propertyList);
    }

    final List internalStructuralPropertiesForType(int apiLevel) {
        return propertyDescriptors(apiLevel);
    }

    final ASTNode internalGetSetChildProperty(ChildPropertyDescriptor property, boolean get, ASTNode child) {
        if (property == LEFT_PROPERTY) {
            if (get) {
                return getLeft();
            } else {
                setLeft((PointcutDesignator) child);
                return null;
            }
        } else if (property == RIGHT_PROPERTY) {
            if (get) {
                return getRight();
            } else {
                setRight((PointcutDesignator) child);
                return null;
            }
        }
        // allow default implementation to flag the error
        return super.internalGetSetChildProperty(property, get, child);
    }

    ASTNode clone0(AST target) {
        OrPointcut result = new OrPointcut(target);
        result.setSourceRange(this.getStartPosition(), this.getLength());
        result.setRight((PointcutDesignator) getRight().clone(target));
        result.setLeft((PointcutDesignator) getLeft().clone(target));
        return result;
    }

    final boolean subtreeMatch0(ASTMatcher matcher, Object other) {
        // dispatch to correct overloaded match method
        return ((AjASTMatcher) matcher).match(this, other);
    }

    void accept0(ASTVisitor visitor) {
        if (visitor instanceof AjASTVisitor) {
            boolean visitChildren = ((AjASTVisitor) visitor).visit(this);
            if (visitChildren) {
                // visit children in normal left to right reading order
                acceptChild(visitor, getLeft());
                acceptChild(visitor, getRight());
            // todo: accept the parameters here
            }
            ((AjASTVisitor) visitor).endVisit(this);
        }
    }

    int treeSize() {
        return memSize() + (this.left == null ? 0 : getLeft().treeSize()) + (this.right == null ? 0 : getRight().treeSize());
    }
}
