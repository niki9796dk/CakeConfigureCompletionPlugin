package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.ConfigFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.ConfigUtil;
import com.github.nizacegodk.cakeconfigplugin.indicies.ConfigKeyStubIndex;
import com.github.nizacegodk.cakeconfigplugin.patterns.ConfigurePattern;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ResourceUtil;
import com.intellij.util.indexing.FileBasedIndex;
import kotlin.Pair;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigGotoDeclarationTarget implements GotoDeclarationHandler {

    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement psiElement, int i, Editor editor) {
        // Only add goto suggestions for configure pattern
        if (! psiElement().with(new ConfigurePattern()).accepts(psiElement)) {
            return new PsiElement[0];
        }

        Project project = editor.getProject();
        if (project == null) {
            return new PsiElement[0];
        }

        String configKey = psiElement.getText().replaceAll("IntellijIdeaRulezzz ", "");

        List<PsiFile> psiFiles = new ArrayList<>();

        List<List<String>> values = FileBasedIndex.getInstance().getValues(
                ConfigKeyStubIndex.KEY,
                configKey,
                GlobalSearchScope.everythingScope(project)
        );

        FileBasedIndex.getInstance().getFilesWithKey(
                ConfigKeyStubIndex.KEY,
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

        List<Pair<PsiFile, Integer>> locations = new ArrayList<>();

        for (PsiFile file : psiFiles) {
            for (List<String> indexPlace : values) {
                String filePath = ConfigUtil.getFilePath(project, file.getVirtualFile());

                if (indexPlace.get(0).equals(filePath)) {
                    locations.add(new Pair<>(file, Integer.parseInt(indexPlace.get(1)) + 1));
                    break;
                }
            }
        }

        ConfigFakePsiElement[] elements = locations.stream()
                .map(jumpLocation -> {
                    PsiElement configPsiElement = jumpLocation.getFirst().findElementAt(jumpLocation.getSecond());
                    String filePath = ConfigUtil.getFilePath(project, jumpLocation.getFirst().getVirtualFile());
                    filePath = StringUtil.trimStart(filePath, "/configs");

                    return new ConfigFakePsiElement(configPsiElement, filePath);
                })
                .sorted(Comparator.comparing(ConfigFakePsiElement::getText))
                .toArray(ConfigFakePsiElement[]::new);

        return elements;
    }
}
