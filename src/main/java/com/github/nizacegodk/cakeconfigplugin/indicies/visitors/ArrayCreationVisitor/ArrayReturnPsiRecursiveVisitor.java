package com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayCreationVisitor;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigKeyDefinitionVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayReturnPsiRecursiveVisitor extends PsiRecursiveElementWalkingVisitor {

    private final ConfigKeyDefinitionVisitor configKeyDefinitionVisitor;

    public ArrayReturnPsiRecursiveVisitor(ConfigKeyDefinitionVisitor configKeyDefinitionVisitor) {
        this.configKeyDefinitionVisitor = configKeyDefinitionVisitor;
    }

    @Override
    public void visitElement(@NotNull PsiElement element) {

        if(element instanceof AssignmentExpressionImpl) {
            if ("$config".equals(element.getFirstChild().getText())) {
                visitConfigAssignment((AssignmentExpressionImpl) element);
            }
        }

        super.visitElement(element);
    }

    public void visitConfigAssignment(AssignmentExpression phpReturn) {
        PsiElement arrayCreation = phpReturn.getValue();

        if(arrayCreation instanceof ArrayCreationExpression) {
            collectConfigKeys((ArrayCreationExpression) arrayCreation, this.configKeyDefinitionVisitor, Collections.emptyList());
        }
    }

    public static void collectConfigKeys(ArrayCreationExpression creationExpression, ConfigKeyDefinitionVisitor configKeyDefinitionVisitor, List<String> context) {

        for(ArrayHashElement hashElement: PsiTreeUtil.getChildrenOfTypeAsList(creationExpression, ArrayHashElement.class)) {

            PsiElement arrayKey = hashElement.getKey();
            PsiElement arrayValue = hashElement.getValue();

            if(arrayKey instanceof StringLiteralExpression) {

                List<String> myContext = new ArrayList<>(context);
                myContext.add(((StringLiteralExpression) arrayKey).getContents());
                String keyName = StringUtils.join(myContext, ".");

                configKeyDefinitionVisitor.visit(keyName, arrayKey);

                if(arrayValue instanceof ArrayCreationExpression) {
                    collectConfigKeys((ArrayCreationExpression) arrayValue, configKeyDefinitionVisitor, myContext);
                }

            }
        }

    }
}
