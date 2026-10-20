package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser;

public class JavaLetterOrDigitMatcher  extends AbstractJavaCharacterMatcher {

    public JavaLetterOrDigitMatcher() {
        super("LetterOrDigit");
    }

    @Override
    protected boolean acceptChar(char c) {
        return Character.isJavaIdentifierPart(c);
    }
}
