package com.github.nizacegodk.cakeconfigplugin;

import com.github.nizacegodk.cakeconfigplugin.indicies.ConfigKeyStubIndex;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ConfigurationCompletionProvider extends CompletionProvider<CompletionParameters> {

    private String[] configs = new String[]{
            "site.phone.minLength",
            "site.phone.maxLength",
            "headers.content.length",
            "apis.safe.username",
            "apis.safe.password",
            "apis.safe.url",
    };

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        String currentConfigKey = this.getCurrentTypedConfigKey(parameters);

        result = result.withPrefixMatcher(currentConfigKey);

        Collection<String> configs = FileBasedIndex.getInstance().getAllKeys(ConfigKeyStubIndex.KEY, parameters.getOriginalFile().getProject());

        for (String conf : configs) {
            ProgressManager.checkCanceled();

            if (conf.startsWith(currentConfigKey)) {
                result.addElement(LookupElementBuilder.create(conf));
            }
        }
    }

    public String getCurrentTypedConfigKey(@NotNull CompletionParameters parameters) {
        return parameters.getPosition().getText().replaceAll("IntellijIdeaRulezzz ", "");
    }

}
