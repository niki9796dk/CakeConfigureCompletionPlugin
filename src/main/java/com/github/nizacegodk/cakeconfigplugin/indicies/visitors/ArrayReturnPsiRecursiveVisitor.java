package com.github.nizacegodk.cakeconfigplugin.indicies.visitors;

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

    private final ArrayKeyVisitor arrayKeyVisitor;

    public ArrayReturnPsiRecursiveVisitor(ArrayKeyVisitor arrayKeyVisitor) {
        this.arrayKeyVisitor = arrayKeyVisitor;
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
            collectConfigKeys((ArrayCreationExpression) arrayCreation, this.arrayKeyVisitor, Collections.emptyList());
        }
    }

    public static void collectConfigKeys(ArrayCreationExpression creationExpression, ArrayKeyVisitor arrayKeyVisitor, List<String> context) {

        for(ArrayHashElement hashElement: PsiTreeUtil.getChildrenOfTypeAsList(creationExpression, ArrayHashElement.class)) {

            PsiElement arrayKey = hashElement.getKey();
            PsiElement arrayValue = hashElement.getValue();

            if(arrayKey instanceof StringLiteralExpression) {

                List<String> myContext = new ArrayList<>(context);
                myContext.add(((StringLiteralExpression) arrayKey).getContents());
                String keyName = StringUtils.join(myContext, ".");

                if(arrayValue instanceof ArrayCreationExpression) {
                    arrayKeyVisitor.visit(keyName, arrayKey, true);
                    collectConfigKeys((ArrayCreationExpression) arrayValue, arrayKeyVisitor, myContext);
                } else {
                    arrayKeyVisitor.visit(keyName, arrayKey, false);
                }

            }
        }

    }
}
