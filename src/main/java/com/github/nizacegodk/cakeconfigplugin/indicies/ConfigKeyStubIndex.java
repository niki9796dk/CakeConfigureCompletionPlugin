package com.github.nizacegodk.cakeconfigplugin.indicies;

import com.github.nizacegodk.cakeconfigplugin.util.ConfigUtil;
import com.github.nizacegodk.cakeconfigplugin.indicies.visitors.ArrayReturnPsiRecursiveVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.externalizer.StringCollectionExternalizer;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class ConfigKeyStubIndex extends FileBasedIndexExtension<String, List<String>> {

    public static final ID<String, List<String>> KEY = ID.create("com.github.nizacegodk.cakeconfigplugin.config_keys");

    @NotNull
    @Override
    public ID<String, List<String>> getName() {
        return KEY;
    }

    @NotNull
    @Override
    public DataIndexer<String, List<String>, FileContent> getIndexer() {
        return fileContent -> {
            final Map<String, List<String>> map = new THashMap<>();

            PsiFile psiFile = fileContent.getPsiFile();

            if(! "PHP".equals(psiFile.getFileType().getName())) {
                return map;
            }

            if (ConfigUtil.isConfigFile(fileContent.getProject(), fileContent.getFile())) {

                psiFile.acceptChildren(new ArrayReturnPsiRecursiveVisitor((key, psiKey, isRootElement) -> {
                    map.put(key, Arrays.asList(
                            ConfigUtil.getFilePath(fileContent.getProject(), fileContent.getFile()),
                            Integer.toString(psiKey.getTextOffset())
                    ));
                }));
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
    public DataExternalizer<List<String>> getValueExternalizer() {
        return StringCollectionExternalizer.STRING_LIST_EXTERNALIZER;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return file -> "PHP".equals(file.getFileType().getName()) && file.getName().endsWith("config.php");
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
