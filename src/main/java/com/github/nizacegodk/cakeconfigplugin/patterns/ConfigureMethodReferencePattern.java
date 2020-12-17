package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.patterns.PatternCondition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl;
import com.jetbrains.php.lang.psi.elements.impl.ParameterListImpl;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigureMethodReferencePattern extends PatternCondition<MethodReference> {

    String validClass = "\\Configure";

    Set<String> validMethods = new HashSet<>(Arrays.asList(
            "\\read",
            "\\write"
    ));

    public ConfigureMethodReferencePattern() {
        super("ConfigurePattern()");
    }

    @Override
    public boolean accepts(@NotNull MethodReference methodReference, ProcessingContext context) {
        ClassReference classReference = (ClassReference) methodReference.getFirstChild();

        return validMethods.contains(methodReference.getFQN()) && this.validClass.equals(classReference.getFQN());
    }
}
