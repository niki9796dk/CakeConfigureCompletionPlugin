package com.github.nizacegodk.cakeconfigplugin.patterns;

import com.intellij.patterns.PatternCondition;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigureMethodReferencePattern extends PatternCondition<MethodReference> {

    String validClass = "\\Configure";

    Set<String> validMethods = new HashSet<>(Arrays.asList(
            "\\read",
            "\\write"
    ));

    public ConfigureMethodReferencePattern() {
        super("ConfigureMethodReferencePattern()");
    }

    public ConfigureMethodReferencePattern(Set<String> validMethods) {
        this();

        if (validMethods != null) {
            this.validMethods = validMethods;
        }
    }

    @Override
    public boolean accepts(@NotNull MethodReference methodReference, ProcessingContext context) {
        if (! (methodReference.getFirstChild() instanceof ClassReference)) {
            return false;
        }

        ClassReference classReference = (ClassReference) methodReference.getFirstChild();

        return validMethods.contains(methodReference.getFQN()) && this.validClass.equals(classReference.getFQN());
    }
}
