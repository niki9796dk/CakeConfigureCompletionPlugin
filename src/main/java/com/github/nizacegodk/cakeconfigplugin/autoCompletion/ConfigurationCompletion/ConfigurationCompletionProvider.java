package com.github.nizacegodk.cakeconfigplugin.autoCompletion.ConfigurationCompletion;

import com.github.nizacegodk.cakeconfigplugin.util.IndicesUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ConfigurationCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        String typedConfigKey = PsiElementUtil.getText(parameters);
        result = result.withPrefixMatcher(typedConfigKey);  // Make sure to only suggest things that start with the current typed string

        // For each config key, check if it has the currently typed config key as prefix
        for (String configKey : IndicesUtil.getAllKeysForConfigKeyIndex(parameters)) {
            ProgressManager.checkCanceled(); // Used to stop the process if the user keeps typing

            if (configKey.startsWith(typedConfigKey)) {
                result.addElement(LookupElementBuilder.create(configKey).withIcon(PlatformIcons.SMALL_VCS_CONFIGURABLE));
            }
        }
    }
}
