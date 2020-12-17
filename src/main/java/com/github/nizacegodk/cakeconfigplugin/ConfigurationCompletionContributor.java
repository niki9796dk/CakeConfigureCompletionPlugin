package com.github.nizacegodk.cakeconfigplugin;

import com.github.nizacegodk.cakeconfigplugin.patterns.ConfigurePattern;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigurationCompletionContributor extends CompletionContributor {
    public ConfigurationCompletionContributor() {
        extend(CompletionType.BASIC,
                psiElement().with(new ConfigurePattern()),
                new ConfigurationCompletionProvider()
        );
    }


}
