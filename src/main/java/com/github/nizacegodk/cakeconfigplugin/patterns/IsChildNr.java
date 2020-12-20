package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.patterns.PatternCondition;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class IsChildNr extends PatternCondition<PsiElement> {

    public static final IsChildNr ONE = new IsChildNr(0);
    public static final IsChildNr TWO = new IsChildNr(1);

    private final int childNr;

    public IsChildNr(int childNr) {
        super("IsFirstChildOf()");
        this.childNr = childNr;
    }

    @Override
    public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext processingContext) {
        PsiElement parent = psiElement.getParent();

        if (parent == null) {
            return false;
        }

        PsiElement[] children = parent.getChildren();

        if (children.length <= this.childNr) {
            return false;
        }

        return children[this.childNr] == psiElement;
    }
}
