package com.github.nizacegodk.cakeconfigplugin.psiElements;

import com.intellij.psi.*;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class ConfigDeclarationFakePsiElement extends DelegatedFakePsiElement {

    private final String filePath;

    public ConfigDeclarationFakePsiElement(PsiElement real, String filePath) {
        super(real);
        this.filePath = filePath;
    }

    @Override
    public @NotNull
    @NonNls String getText() {
        return this.filePath;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return PlatformIcons.SMALL_VCS_CONFIGURABLE;
    }
}
