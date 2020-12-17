package com.github.nizacegodk.cakeconfigplugin.autoCompletion.ConfigurationCompletion;

import com.github.nizacegodk.cakeconfigplugin.patterns.CakeConfigureStringArgumentPattern;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigurationCompletionContributor extends CompletionContributor {
    public ConfigurationCompletionContributor() {
        extend(CompletionType.BASIC,
                psiElement().with(new CakeConfigureStringArgumentPattern()),
                new ConfigurationCompletionProvider()
        );
    }


}
