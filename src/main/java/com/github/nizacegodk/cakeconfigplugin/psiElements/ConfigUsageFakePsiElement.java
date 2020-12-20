package com.github.nizacegodk.cakeconfigplugin.psiElements;

import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.PsiElement;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigUsageFakePsiElement extends DelegatedFakePsiElement {

    private final String filePath;

    public ConfigUsageFakePsiElement(PsiElement real, String filePath) {
        super(real);
        this.filePath = filePath;
    }

    @Override
    @NotNull
    public String getPresentableText() {
        return PsiElementUtil.getText(this.real);
    }

    @Override
    public @Nullable String getLocationString() {
        return this.filePath + ":" + PsiElementUtil.getLineNumber(real);
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return PlatformIcons.SMALL_VCS_CONFIGURABLE;
    }
}
