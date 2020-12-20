package com.github.nizacegodk.cakeconfigplugin.indicies.visitors;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayCreationVisitor.ArrayReturnPsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.patterns.CakeConfigureStringArgumentPattern;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigureReadPsiRecursiveVisitor extends PsiRecursiveElementWalkingVisitor {

    private final ConfigKeyDefinitionVisitor configKeyDefinitionVisitor;

    public ConfigureReadPsiRecursiveVisitor(ConfigKeyDefinitionVisitor configKeyDefinitionVisitor) {
        this.configKeyDefinitionVisitor = configKeyDefinitionVisitor;
    }

    @Override
    public void visitElement(@NotNull PsiElement element) {

        if (this.isFirstArgumentOfConfigureRead(element)) {
            // Extract the config key, and visit that element so we can add the key to our index
            String configKey = PsiElementUtil.getText(element);
            this.configKeyDefinitionVisitor.visit(configKey, element);
        }

        super.visitElement(element);
    }

    private boolean isFirstArgumentOfConfigureRead(PsiElement psiElement) {
        return psiElement().with(new CakeConfigureStringArgumentPattern(Collections.singleton("\\read"))).accepts(psiElement);
    }
}
