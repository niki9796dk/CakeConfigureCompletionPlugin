package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.patterns.PatternCondition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl;
import com.jetbrains.php.lang.psi.elements.impl.ParameterListImpl;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigurePattern extends PatternCondition<PsiElement> {

    public ConfigurePattern() {
        super("ConfigurePattern()");
    }

    @Override
    public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext context) {
        // find out about the current language's string and comment token types
        Language lang = PsiUtilCore.findLanguageFromElement(psiElement);
        ParserDefinition definition = LanguageParserDefinitions.INSTANCE.forLanguage(lang);

        if (definition == null) {
            return false;
        }

        ASTNode node = psiElement.getNode();

        if (node == null) {
            return false;
        }

        PsiElement parent = psiElement.getParent();
        if (! (parent instanceof StringLiteralExpressionImpl)) {
            return false;
        }

        parent = parent.getParent();
        if (! (parent instanceof ParameterListImpl)) {
            return false;
        }

        parent = parent.getParent();
        if (! (parent instanceof MethodReferenceImpl)) {
            return false;
        }

        MethodReference methodReference = (MethodReference) parent;
        ClassReference classReference = (ClassReference) methodReference.getFirstChild();

        Set<String> validMethods = new HashSet<>(Arrays.asList("\\read", "\\write"));

        return validMethods.contains(methodReference.getFQN()) && "\\Configure".equals(classReference.getFQN());
    }
}
