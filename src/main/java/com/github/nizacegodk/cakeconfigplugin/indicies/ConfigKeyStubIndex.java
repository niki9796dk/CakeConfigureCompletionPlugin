package com.github.nizacegodk.cakeconfigplugin.indicies;

import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ConfigureWritePsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayCreationVisitor.ArrayReturnPsiRecursiveVisitor;
import com.github.nizacegodk.cakeconfigplugin.util.PsiFileUtil;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import com.jetbrains.php.lang.PhpFileType;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class ConfigKeyStubIndex extends FileBasedIndexExtension<String, Void> {

    public static final ID<String, Void> KEY = ID.create("com.github.nizacegodk.cakeconfigplugin.config_keys");

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return KEY;
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return fileContent -> {

            final Map<String, Void> map = new THashMap<>();
            PsiFile psiFile = fileContent.getPsiFile();

            if (PsiFileUtil.isTestFile(fileContent)) {
                return map;
            }

            // Collect all Configure::write statements
            psiFile.acceptChildren(new ConfigureWritePsiRecursiveVisitor((key, psiElement) -> map.put(key, null)));

            // Collect all $config = [...] statements within config files
            if (PsiFileUtil.isConfigFile(fileContent)) {
                psiFile.acceptChildren(new ArrayReturnPsiRecursiveVisitor((key, psiElement) -> map.put(key, null)));
            }

            return map;
        };
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @NotNull
    @Override
    public DataExternalizer<Void> getValueExternalizer() {
        return VoidDataExternalizer.INSTANCE;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return file -> file.getFileType() == PhpFileType.INSTANCE;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @Override
    public int getVersion() {
        return 1;
    }


}
