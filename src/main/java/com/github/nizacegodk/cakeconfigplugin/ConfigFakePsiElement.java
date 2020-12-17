package com.github.nizacegodk.cakeconfigplugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.model.psi.PsiSymbolReference;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigFakePsiElement extends FakePsiElement {

    private final PsiElement real;
    private String filePath;

    public ConfigFakePsiElement(PsiElement real, String filePath) {
        this.real = real;
        this.filePath = filePath;
    }

    @Override
    public PsiElement getParent() {
        return real.getParent();
    }

    @Override
    public @NotNull TextRange getTextRangeInParent() {
        return real.getTextRangeInParent();
    }

    @Override
    public @NotNull Iterable<? extends PsiSymbolReference> getOwnReferences() {
        return real.getOwnReferences();
    }

    @Override
    public @Nullable
    @NonNls String getText() {
        return this.filePath;
    }

    @Override
    @Contract(pure = true)
    public @NotNull Project getProject() throws PsiInvalidElementAccessException {
        return real.getProject();
    }

    @Override
    @Contract(pure = true)
    public @NotNull Language getLanguage() {
        return real.getLanguage();
    }

    @Override
    @Contract(pure = true)
    public PsiManager getManager() {
        return real.getManager();
    }

    @Override
    @Contract(pure = true)
    public PsiElement[] getChildren() {
        return real.getChildren();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public PsiElement getFirstChild() {
        return real.getFirstChild();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public PsiElement getLastChild() {
        return real.getLastChild();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public PsiElement getNextSibling() {
        return real.getNextSibling();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public PsiElement getPrevSibling() {
        return real.getPrevSibling();
    }

    @Override
    @Contract(pure = true)
    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        return real.getContainingFile();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public TextRange getTextRange() {
        return real.getTextRange();
    }

    @Override
    @Contract(pure = true)
    public int getStartOffsetInParent() {
        return real.getStartOffsetInParent();
    }

    @Override
    @Contract(pure = true)
    public int getTextLength() {
        return real.getTextLength();
    }

    @Override
    @Contract(pure = true)
    @Nullable
    public PsiElement findElementAt(int i) {
        return real.findElementAt(i);
    }

    @Override
    @Contract(pure = true)
    public @Nullable PsiReference findReferenceAt(int i) {
        return real.findReferenceAt(i);
    }

    @Override
    @Contract(pure = true)
    public int getTextOffset() {
        return real.getTextOffset();
    }

    @Override
    @Contract(pure = true)
    public char[] textToCharArray() {
        return real.textToCharArray();
    }

    @NotNull
    @Override
    @Contract(pure = true)
    public PsiElement getNavigationElement() {
        return real.getNavigationElement();
    }

    @Override
    @Contract(pure = true)
    public PsiElement getOriginalElement() {
        return real.getOriginalElement();
    }

    @Override
    @Contract(pure = true)
    public boolean textMatches(@NotNull @NonNls CharSequence charSequence) {
        return real.textMatches(charSequence);
    }

    @Override
    @Contract(pure = true)
    public boolean textMatches(@NotNull PsiElement psiElement) {
        return real.textMatches(psiElement);
    }

    @Override
    @Contract(pure = true)
    public boolean textContains(char c) {
        return real.textContains(c);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor psiElementVisitor) {
        real.accept(psiElementVisitor);
    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor psiElementVisitor) {
        real.acceptChildren(psiElementVisitor);
    }

    @Override
    public PsiElement copy() {
        return real.copy();
    }

    @Override
    public PsiElement add(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return real.add(psiElement);
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
        return real.addBefore(psiElement, psiElement1);
    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
        return real.addAfter(psiElement, psiElement1);
    }

    @Override
    @Deprecated
    public void checkAdd(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        real.checkAdd(psiElement);
    }

    @Override
    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {
        return real.addRange(psiElement, psiElement1);
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
        return real.addRangeBefore(psiElement, psiElement1, psiElement2);
    }

    @Override
    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
        return real.addRangeAfter(psiElement, psiElement1, psiElement2);
    }

    @Override
    public void delete() throws IncorrectOperationException {
        real.delete();
    }

    @Override
    @Deprecated
    public void checkDelete() throws IncorrectOperationException {
        real.checkDelete();
    }

    @Override
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {
        real.deleteChildRange(psiElement, psiElement1);
    }

    @Override
    public PsiElement replace(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return real.replace(psiElement);
    }

    @Override
    @Contract(pure = true)
    public boolean isValid() {
        return real.isValid();
    }

    @Override
    @Contract(pure = true)
    public boolean isWritable() {
        return real.isWritable();
    }

    @Override
    @Contract(pure = true)
    public @Nullable PsiReference getReference() {
        return real.getReference();
    }

    @Override
    @Contract(pure = true)
    public PsiReference[] getReferences() {
        return real.getReferences();
    }

    @Override
    @Contract(pure = true)
    public <T> T getCopyableUserData(Key<T> key) {
        return real.getCopyableUserData(key);
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, @Nullable T t) {
        real.putCopyableUserData(key, t);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @Nullable PsiElement psiElement, @NotNull PsiElement psiElement1) {
        return real.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement1);
    }

    @Override
    @Contract(pure = true)
    @Nullable
    public PsiElement getContext() {
        return real.getContext();
    }

    @Override
    @Contract(pure = true)
    public boolean isPhysical() {
        return real.isPhysical();
    }

    @Override
    @Contract(pure = true)
    public @NotNull GlobalSearchScope getResolveScope() {
        return real.getResolveScope();
    }

    @Override
    @Contract(pure = true)
    public @NotNull SearchScope getUseScope() {
        return real.getUseScope();
    }

    @Nullable
    @Override
    @Contract(pure = true)
    public ASTNode getNode() {
        return real.getNode();
    }

    @Override
    @Contract(pure = true)
    @NonNls
    public String toString() {
        return real.toString();
    }

    @Override
    @Contract(pure = true)
    public boolean isEquivalentTo(PsiElement psiElement) {
        return real.isEquivalentTo(psiElement);
    }

    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return real.getUserData(key);
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {
        real.putUserData(key, t);
    }

    @Override
    public @Nullable Icon getIcon(boolean open) {
        return PlatformIcons.SMALL_VCS_CONFIGURABLE;
    }
}
