package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.github.nizacegodk.cakeconfigplugin.util.PhpLangUtil;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;

import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class CakeConfigureStringArgumentPattern extends CapturePatternCondition<PsiElement> {

    Set<String> validMethods = null;

    public CakeConfigureStringArgumentPattern() {
        super("CakeConfigureStringArgumentPattern()");
    }

    public CakeConfigureStringArgumentPattern(Set<String> validMethods) {
        this();
        this.validMethods = validMethods;
    }

    protected PsiElementPattern.Capture<PsiElement> getFilter() {
        return psiElement().withElementType(PhpLangUtil.stringLiterals()).withParent(
                psiElement(StringLiteralExpression.class).with(IsChildNr.ONE).withParent(
                        psiElement(ParameterList.class).withParent(
                                psiElement(MethodReference.class).with(new ConfigureMethodReferencePattern(validMethods))
                        )
                )
        );
    }
}
