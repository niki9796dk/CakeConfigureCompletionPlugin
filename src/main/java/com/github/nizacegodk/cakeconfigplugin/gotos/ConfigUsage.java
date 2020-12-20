package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigDeclarationFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigUsageFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression;
import com.jetbrains.php.lang.psi.elements.ArrayHashElement;
import com.jetbrains.php.lang.psi.elements.ParameterList;

public class ConfigUsage {
    public final String filePath;
    public final ConfigUsageFakePsiElement psiElement;

    public ConfigUsage(PsiElement actualPsiElement, String filePath) {
        this.filePath = filePath;

        this.psiElement = new ConfigUsageFakePsiElement(actualPsiElement, filePath);
    }


}
