package com.github.nizacegodk.cakeconfigplugin.indicies.visitors;

import com.intellij.psi.PsiElement;

public interface ConfigKeyDefinitionVisitor {
    void visit(String key, PsiElement psiKey);
}
