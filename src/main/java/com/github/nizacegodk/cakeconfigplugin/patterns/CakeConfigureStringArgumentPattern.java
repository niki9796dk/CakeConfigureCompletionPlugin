package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class CakeConfigureStringArgumentPattern extends CapturePatternCondition<PsiElement> {

    public CakeConfigureStringArgumentPattern() {
        super("ConfigurePattern()");
    }

    protected PsiElementPattern.Capture<PsiElement> getFilter() {
        return psiElement().withParent(
                psiElement(StringLiteralExpression.class).withParent(
                        psiElement(ParameterList.class).withParent(
                                psiElement(MethodReference.class).with(new ConfigureMethodReferencePattern())
                        )
                )
        );
    }
}
