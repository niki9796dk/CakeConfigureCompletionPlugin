package com.github.nizacegodk.cakeconfigplugin.util;

import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.psi.tree.TokenSet;
import com.jetbrains.php.lang.PhpLanguage;

public class PhpLangUtil {
    public static TokenSet stringLiterals() {
        return LanguageParserDefinitions.INSTANCE.forLanguage(PhpLanguage.INSTANCE).getStringLiteralElements();
    }
}
