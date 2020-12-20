package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigDeclarationFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.*;

public class ConfigDeclaration {
    public final PsiFile psiFile;
    public final String filePath;
    public final ConfigDeclarationFakePsiElement psiElement;

    public ConfigDeclaration(PsiFile psiFile, String filePath, PsiElement actualPsiElement) {
        this.psiFile = psiFile;
        this.filePath = filePath;

        this.psiElement = new ConfigDeclarationFakePsiElement(actualPsiElement, filePath, this.getValueText(actualPsiElement));
    }

    private String getValueText(PsiElement configElement) {
        PsiElement configValue = this.getValueElementForConfig(configElement);

        return this.getValueTextFromValueElement(configValue);
    }

    private String getValueTextFromValueElement(PsiElement valueElement) {
        if (valueElement == null) {
            return "Mixed";
        }

        if (valueElement instanceof ArrayCreationExpression) {
            return "Array";
        }

        String returnText = PsiElementUtil.getText(valueElement);

        if (returnText.length() > 30) {
            return returnText.substring(0, 30) + "...";
        }

        return returnText;
    }

    private PsiElement getValueElementForConfig(PsiElement declaration) {
        PsiElement wrappingParent = declaration.getParent().getParent();

        // Configure::write
        if (wrappingParent instanceof ParameterList) {
            return wrappingParent.getLastChild();
        }

        // Config array
        if (wrappingParent instanceof ArrayHashElement) {
            return ((ArrayHashElement) wrappingParent).getValue();
        }

        return null;
    }

}
