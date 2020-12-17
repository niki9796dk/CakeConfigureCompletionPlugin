package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigDeclarationFakePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class ConfigDeclaration {
    public final PsiFile psiFile;
    public final String filePath;
    public final ConfigDeclarationFakePsiElement psiElement;

    public ConfigDeclaration(PsiFile psiFile, String filePath, String declarationOffset) {
        this.psiFile = psiFile;
        this.filePath = filePath;

        PsiElement actualPsiElement = psiFile.findElementAt(Integer.parseInt(declarationOffset) + 1);
        this.psiElement = new ConfigDeclarationFakePsiElement(actualPsiElement, filePath);
    }

}
