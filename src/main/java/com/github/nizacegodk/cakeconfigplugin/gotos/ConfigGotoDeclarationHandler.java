package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayCreationVisitor.ArrayReturnPsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigKeyDefinitionVisitor;
import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigureWritePsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigDeclarationFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.patterns.CakeConfigureStringArgumentPattern;
import com.github.nizacegodk.cakeconfigplugin.util.IndicesUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PsiFileUtil;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigGotoDeclarationHandler implements GotoDeclarationHandler {

    private Project project;

    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement psiElement, int i, Editor editor) {
        // Only add goto suggestions for configure pattern
        if (this.isInvalidCall(psiElement, i, editor)) {
            return new PsiElement[0];
        }

        String configKey = PsiElementUtil.getText(psiElement);

        List<ConfigDeclaration> declarations = this.getAllDeclarationsForConfig(configKey, this.project);

        return declarations
                .stream()
                .map(declaration -> declaration.psiElement)
                .sorted(Comparator.comparing(ConfigDeclarationFakePsiElement::getText))
                .toArray(ConfigDeclarationFakePsiElement[]::new);
    }

    private boolean isInvalidCall(@Nullable PsiElement psiElement, int i, Editor editor) {
        // Only add goto suggestions for configure pattern
        if (! psiElement().with(new CakeConfigureStringArgumentPattern()).accepts(psiElement)) {
            return true;
        }

        // And only add goto suggestions when we can extract the project
        Project project = editor.getProject();
        if (project == null) {
            return true;
        }

        this.project = project;

        return false;
    }

    private List<ConfigDeclaration> getAllDeclarationsForConfig(String configKey, Project project) {

        // Find all declarations and find all PsiFiles with those declarations
        List<PsiFile> configurationFiles = IndicesUtil.getAllFilesWithConfigKey(configKey, project);

        List<ConfigDeclaration> declarations = new ArrayList<>();

        for (PsiFile configurationFile : configurationFiles) {
            String filePath = PsiFileUtil.getFilePath(project, configurationFile.getVirtualFile());

            ConfigKeyDefinitionVisitor visitor = (key, psiKey) -> {
                if (key.equals(configKey)) {
                    declarations.add(new ConfigDeclaration(configurationFile, filePath, psiKey));
                }
            };

            configurationFile.acceptChildren(new ConfigureWritePsiRecursiveVisitor(visitor));

            if (PsiFileUtil.isConfigFile(configurationFile)) {
                configurationFile.acceptChildren(new ArrayReturnPsiRecursiveVisitor(visitor));
            }
        }

        return declarations;
    }
}
