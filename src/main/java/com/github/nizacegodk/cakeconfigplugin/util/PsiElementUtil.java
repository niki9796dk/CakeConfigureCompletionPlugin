package com.github.nizacegodk.cakeconfigplugin.util;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class PsiElementUtil {

    /**
     * Returns the content of a psiElement
     *
     * The ide for some reason append "IntellijIdeaRulezzz " to the end of the string, so we need to remove that.
     *
     * @param psiElement The element to extract the text from
     *
     * @return string
     */
    public static String getText(@NotNull PsiElement psiElement) {
        return StringUtil.trimEnd(psiElement.getText(), "IntellijIdeaRulezzz ");
    }

    public static String getText(@NotNull CompletionParameters parameters) {
        return PsiElementUtil.getText(parameters.getPosition());
    }

    public static int getLineNumber(PsiElement psiElement) {
        PsiFile containingFile = psiElement.getContainingFile();
        FileViewProvider fileViewProvider = containingFile.getViewProvider();
        Document document = fileViewProvider.getDocument();

        if (document == null) {
            throw new IllegalArgumentException("Cant find linenumber for psiElement!");
        }

        int textOffset = psiElement.getTextOffset();
        return document.getLineNumber(textOffset) + 1;
    }

}
