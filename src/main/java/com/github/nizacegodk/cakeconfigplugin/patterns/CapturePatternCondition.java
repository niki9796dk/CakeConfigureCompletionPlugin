package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

abstract public class CapturePatternCondition<T extends PsiElement> extends PatternCondition<T> {

    public CapturePatternCondition(String debugMethodName) {
        super(debugMethodName);
    }

    @Override
    public boolean accepts(@NotNull T psiElement, ProcessingContext context) {
        return this.getFilter().accepts(psiElement);
    }
    
    abstract protected PsiElementPattern.Capture<T> getFilter();
}
