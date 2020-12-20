package com.github.nizacegodk.cakeconfigplugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.FileContent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PsiFileUtil {
    private static final Pattern testDir = Pattern.compile("([\\w-/]+)/[Tt]est(s)?/([\\w-/]+).php");
    private static final Pattern testFile = Pattern.compile("([\\w-/]+)[tT]est.php");

    private static final Pattern configFilePathPattern = Pattern.compile("/configs/([\\w-/]+).php$");


    public static boolean isTestFile(PsiFile psiFile) {
        String filePath = PsiFileUtil.getFilePath(psiFile);

        return testDir.matcher(filePath).matches() || testFile.matcher(filePath).matches();
    }

    public static boolean isTestFile(FileContent fileContent) {
        String filePath = PsiFileUtil.getFilePath(fileContent);

        return testDir.matcher(filePath).matches() || testFile.matcher(filePath).matches();
    }

    public static boolean isNotTestFile(PsiFile psiFile) {
        return ! PsiFileUtil.isTestFile(psiFile);
    }

    public static boolean isNotTestFile(FileContent fileContent) {
        return ! PsiFileUtil.isTestFile(fileContent);
    }

    public static String getFilePath(Project project, VirtualFile virtualFile) {
        String projectPath = project.getBasePath();
        projectPath = projectPath == null ? "" : projectPath;

        return StringUtil.trimStart(virtualFile.getPath(), projectPath);
    }

    public static String getFilePath(PsiFile psiFile) {
        return PsiFileUtil.getFilePath(psiFile.getProject(), psiFile.getVirtualFile());
    }

    public static String getFilePath(FileContent fileContent) {
        return PsiFileUtil.getFilePath(fileContent.getProject(), fileContent.getFile());
    }

    public static boolean isConfigFile(PsiFile psiFile) {
        String path = PsiFileUtil.getFilePath(psiFile);
        Matcher mather = configFilePathPattern.matcher(path);

        return mather.matches();
    }

    public static boolean isConfigFile(FileContent fileContent) {
        String path = PsiFileUtil.getFilePath(fileContent);
        Matcher mather = configFilePathPattern.matcher(path);

        return mather.matches();
    }
}
