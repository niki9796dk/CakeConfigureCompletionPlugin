package com.github.nizacegodk.cakeconfigplugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigUtil {
    private static final Pattern configFilePathPattern = Pattern.compile("/configs/(.+/)*config.php$");

    public static boolean isConfigFile(Project project, VirtualFile virtualFile) {
        String path = ConfigUtil.getFilePath(project, virtualFile);
        Matcher mather = configFilePathPattern.matcher(path);

        return mather.matches();
    }

    public static String getFilePath(Project project, VirtualFile virtualFile) {
        String projectPath = project.getBasePath();
        projectPath = projectPath == null ? "" : projectPath;

        return StringUtil.trimStart(virtualFile.getPath(), projectPath);
    }

}
