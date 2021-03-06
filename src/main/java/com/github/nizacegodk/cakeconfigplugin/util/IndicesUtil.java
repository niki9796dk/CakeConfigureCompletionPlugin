package com.github.nizacegodk.cakeconfigplugin.util;

import com.github.nizacegodk.cakeconfigplugin.indicies.ConfigKeyDeclarationIndex;
import com.github.nizacegodk.cakeconfigplugin.indicies.ConfigKeyUsageIndex;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.ID;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IndicesUtil {

    public static Collection<String> getAllKeysForConfigKeyIndex(@NotNull CompletionParameters parameters) {
        return IndicesUtil.getAllKeysForConfigKeyIndex(parameters.getOriginalFile().getProject());
    }

    public static Collection<String> getAllKeysForConfigKeyIndex(@NotNull Project project) {
        return IndicesUtil.getAllKeys(ConfigKeyDeclarationIndex.KEY, project);
    }

    protected static <K> Collection<K> getAllKeys(@NotNull ID<K, ?> indexKey, @NotNull Project project) {
        return FileBasedIndex.getInstance().getAllKeys(indexKey, project);
    }

    public static List<PsiFile> getAllFilesWithConfigKeyDefinition(@NotNull String configKey, @NotNull Project project) {
        List<PsiFile> psiFiles = new ArrayList<>();

        FileBasedIndex.getInstance().getFilesWithKey(
                ConfigKeyDeclarationIndex.KEY,
                Collections.singleton(configKey),
                virtualFile -> {
                    PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

                    if(psiFile == null) {
                        return true;
                    }

                    psiFiles.add(psiFile);

                    return true;
                },
                GlobalSearchScope.everythingScope(project)
        );

        return psiFiles;
    }

    public static List<PsiFile> getAllFilesWithConfigKeyUsage(@NotNull String configKey, @NotNull Project project) {
        List<PsiFile> psiFiles = new ArrayList<>();

        FileBasedIndex.getInstance().getFilesWithKey(
                ConfigKeyUsageIndex.KEY,
                Collections.singleton(configKey),
                virtualFile -> {
                    PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

                    if(psiFile == null) {
                        return true;
                    }

                    psiFiles.add(psiFile);

                    return true;
                },
                GlobalSearchScope.everythingScope(project)
        );

        return psiFiles;
    }

    protected static <K, V> List<V> getValues(@NotNull ID<K, V> indexKey, @NotNull K key, @NotNull GlobalSearchScope searchScope) {
        return FileBasedIndex.getInstance().getValues(indexKey, key, searchScope);
    }

}
