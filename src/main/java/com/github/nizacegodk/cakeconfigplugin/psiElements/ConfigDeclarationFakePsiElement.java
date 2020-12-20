package com.github.nizacegodk.cakeconfigplugin.psiElements;

import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.*;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigDeclarationFakePsiElement extends DelegatedFakePsiElement {

    private final String filePath;
    private final String valueText;

    public ConfigDeclarationFakePsiElement(PsiElement real, String filePath, String valueText) {
        super(real);
        this.filePath = filePath;
        this.valueText = valueText;
    }

    @Override
    public @NotNull
    @NonNls String getText() {
        return this.filePath + ":" + PsiElementUtil.getLineNumber(this);
    }

    @Override
    public String getPresentableText() {
        return this.valueText;
    }

    @Override
    public @Nullable String getLocationString() {
        return this.filePath + ":" + PsiElementUtil.getLineNumber(this);
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return PlatformIcons.SMALL_VCS_CONFIGURABLE;
    }
}
