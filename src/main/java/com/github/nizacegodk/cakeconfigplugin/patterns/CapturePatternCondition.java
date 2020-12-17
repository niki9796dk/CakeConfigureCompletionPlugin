package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

abstract public class CapturePatternCondition<T> extends PatternCondition<T> {

    public CapturePatternCondition(String debugMethodName) {
        super(debugMethodName);
    }

    @Override
    public boolean accepts(@NotNull T psiElement, ProcessingContext context) {
        return this.getFilter().accepts(psiElement);
    }
    
    abstract protected PsiElementPattern.Capture<PsiElement> getFilter();
}
