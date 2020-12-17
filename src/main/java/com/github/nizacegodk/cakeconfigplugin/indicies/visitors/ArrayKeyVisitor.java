package com.github.nizacegodk.cakeconfigplugin.indicies.visitors;

import com.intellij.psi.PsiElement;

public interface ArrayKeyVisitor {
    void visit(String key, PsiElement psiKey, boolean isRootElement);
}
