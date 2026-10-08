package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser;

public class JavaLetterMatcher extends AbstractJavaCharacterMatcher {

    public JavaLetterMatcher() {
        super("Letter");
    }

    @Override
    protected boolean acceptChar(char c) {
        return Character.isJavaIdentifierStart(c);
    }
}