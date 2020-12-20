package com.github.nizacegodk.cakeconfigplugin.gotos;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigKeyDefinitionVisitor;
import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigureReadPsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.patterns.IsChildNr;
import com.github.nizacegodk.cakeconfigplugin.psiElements.ConfigUsageFakePsiElement;
import com.github.nizacegodk.cakeconfigplugin.util.IndicesUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PhpLangUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.github.nizacegodk.cakeconfigplugin.util.PsiFileUtil;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.elements.ArrayHashElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import java.util.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigGotoUsageHandler implements GotoDeclarationHandler {

    private Project project;

    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement psiElement, int i, Editor editor) {
        // Only add goto suggestions for keys in config files
        if (this.isInvalidCall(psiElement, i, editor)) {
            return new PsiElement[0];
        }

        String configKey = this.getConfigKeyFromConfigArray(psiElement);

        List<ConfigUsage> usages = this.getAllUsagesForConfig(configKey, this.project);

        return usages
                .stream()
                .map(declaration -> declaration.psiElement)
                .sorted(Comparator.comparing(ConfigUsageFakePsiElement::getPresentableText))
                .toArray(ConfigUsageFakePsiElement[]::new);
    }

    private String getConfigKeyFromConfigArray(PsiElement configKeyElement) {
        List<String> keyParts = new ArrayList<>();

        PsiElement parent = configKeyElement.getParent();

        do {

            if (parent instanceof ArrayHashElement) {
                PsiElement grandChild = parent.getFirstChild().getFirstChild();

                if (grandChild instanceof StringLiteralExpression) {
                    keyParts.add(((StringLiteralExpression) grandChild).getContents());
                }
            }

            parent = parent.getParent();

        } while (parent != null);

        Collections.reverse(keyParts);

        return StringUtil.join(keyParts, ".");
    }

    private boolean isInvalidCall(@Nullable PsiElement psiElement, int i, Editor editor) {
        if (psiElement == null) {
            return true;
        }

        PsiFile file = psiElement.getContainingFile();

        if (!PsiFileUtil.isConfigFile(file)) {
            return true;
        }

        // And only add goto suggestions when we can extract the project
        Project project = editor.getProject();
        if (project == null) {
            return true;
        }

        this.project = project;

        // Is config key definition
        return ! this.getConfigFileKeyCapture().accepts(psiElement);
    }

    private PsiElementPattern.Capture<PsiElement> getConfigFileKeyCapture() {
        return psiElement().withElementType(PhpLangUtil.stringLiterals()).withParent(
                psiElement(StringLiteralExpression.class).withParent(
                        psiElement().with(IsChildNr.ONE).withParent(
                                psiElement(ArrayHashElement.class)
                        )
                )
        );
    }

    private List<ConfigUsage> getAllUsagesForConfig(String configKey, Project project) {
        // Find all files that uses the config
        List<PsiFile> configurationFiles = IndicesUtil.getAllFilesWithConfigKeyUsage(configKey, project);

        // Find all usages in each file and return them
        List<ConfigUsage> usages = new ArrayList<>();

        for (PsiFile configurationFile : configurationFiles) {
            String filePath = PsiFileUtil.getFilePath(project, configurationFile.getVirtualFile());

            ConfigKeyDefinitionVisitor visitor = (key, psiKey) -> {
                if (key.startsWith(configKey)) {
                    usages.add(new ConfigUsage(psiKey, filePath));
                }
            };

            configurationFile.acceptChildren(new ConfigureReadPsiRecursiveVisitor(visitor));
        }

        return usages;
    }
}
