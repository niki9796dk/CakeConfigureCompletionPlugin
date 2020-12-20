package com.github.nizacegodk.cakeconfigplugin.indicies.visitors;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayCreationVisitor.ArrayReturnPsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.patterns.CakeConfigureStringArgumentPattern;
import com.github.nizacegodk.cakeconfigplugin.util.PsiElementUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ConfigureWritePsiRecursiveVisitor extends PsiRecursiveElementWalkingVisitor {

    private final ConfigKeyDefinitionVisitor configKeyDefinitionVisitor;

    public ConfigureWritePsiRecursiveVisitor(ConfigKeyDefinitionVisitor configKeyDefinitionVisitor) {
        this.configKeyDefinitionVisitor = configKeyDefinitionVisitor;
    }

    @Override
    public void visitElement(@NotNull PsiElement element) {

        if (this.isFirstArgumentOfConfigureWrite(element)) {
            // Extract the config key, and visit that element so we can add the key to our index
            String configKey = PsiElementUtil.getText(element);
            this.configKeyDefinitionVisitor.visit(configKey, element);

            // Check the value of the configure write. If it is an array, we need to recursively visit it to extract all the subkeys
            PsiElement configValue = this.getSecondArgumentOfConfigureWrite(element);

            if (configValue instanceof ArrayCreationExpression) {
                ArrayReturnPsiRecursiveVisitor.collectConfigKeys((ArrayCreationExpression) configValue, this.configKeyDefinitionVisitor, Collections.singletonList(configKey));
            }
        }

        super.visitElement(element);
    }

    private boolean isFirstArgumentOfConfigureWrite(PsiElement psiElement) {
        return psiElement().with(new CakeConfigureStringArgumentPattern(Collections.singleton("\\write"))).accepts(psiElement);
    }

    private PsiElement getSecondArgumentOfConfigureWrite(PsiElement configureWriteKey) {
        ParameterList parameterList = (ParameterList) configureWriteKey.getParent().getParent();

        PsiElement[] parameters = parameterList.getChildren();

        if (parameters.length < 2) {
            return null;
        }

        return parameters[1];
    }
}
