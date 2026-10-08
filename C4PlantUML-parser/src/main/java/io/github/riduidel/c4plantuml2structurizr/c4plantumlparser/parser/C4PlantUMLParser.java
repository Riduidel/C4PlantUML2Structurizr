package io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.parser;

import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Context;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.Include;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.PersonBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.PlantUMLBlock;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.RelationshipBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.SoftwareSystemBuilder;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WithAlias;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WithDescription;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WithLabel;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WithTechnology;
import io.github.riduidel.c4plantuml2structurizr.c4plantumlparser.model.WorkspaceElementBuilder;

/**
 * Parses a plantuml diagram.
 * This class has object as generic type to allow pushing string values on stack
 * as well as {@link WorkspaceElementBuilder} (which are the main objects to use)
 */
@BuildParseTree
public class C4PlantUMLParser extends BaseParser<WorkspaceElementBuilder> {

	public Rule diagram() {
		return Sequence(
				Sequence(String("@startuml"), ZeroOrMore(NoneOf("\n")), push(new PlantUMLBlock(match().trim())), String("\n")
						),
				ZeroOrMore(
						Include(),
						// There can be empty lines
						Whitespace(),
						Person(),
						System(),
						Relationship()
						).label("diagram content"),
				String("@enduml"),
				// Add this node to its parent
				peek(1).add(pop()));
		
	}
	
	void setSourceAlias(Context context) {
		((RelationshipBuilder) context.getValueStack().peek()).setSourceAlias(match());
	}
	
	void setTargetAlias(Context context) {
		((RelationshipBuilder) context.getValueStack().peek()).setTargetAlias(match());
	}
	
	public Rule Relationship() {
		return Sequence(String("Rel"),
				Optional(String("Ext")),
				Ch('('),
				push(new RelationshipBuilder()),
				AliasDeclaration(this::setSourceAlias),
				Ch(','),
				AliasDeclaration(this::setTargetAlias),
				ZeroOrMore(
					DescriptionDeclaration(),
					ZeroOrMore(
							TechnologyDeclaration()
						)
					),
				Ch(')'),
				Whitespace(),
				// Add relationship to its parent node
				peek(1).add(pop())
				)
				.label("RelationshipBuilder...(....)");
	}

	public Rule Person() {
		return Sequence(String("Person"),
				Optional(String("Ext")),
				Ch('('),
				push(new PersonBuilder()),
				AliasDeclaration(),
				LabelDeclaration(),
				ZeroOrMore(
					DescriptionDeclaration()
					),
				Ch(')'),
				Whitespace(),
				// Add person to its parent node
				peek(1).add(pop())
				)
				.label("PersonBuilder...(....)");
	}
	
	public Rule System() {
		return Sequence(String("System"),
				Optional(String("Ext")),
				Ch('('),
				push(new SoftwareSystemBuilder()),
				AliasDeclaration(),
				LabelDeclaration(),
				ZeroOrMore(
					DescriptionDeclaration()
					),
				Ch(')'),
				Whitespace(),
				// Add relationship to its parent node
				peek(1).add(pop())
				)
				.label("SystemBuilder...(....)");
	}
	
	void setDescription(Context context) {
		String description = getCleanedStringLiteralMatch(context);
		((WithDescription) context.getValueStack().peek()).withDescription(description);
	}

	static String getCleanedStringLiteralMatch(Context context) {
		return StringEscapeUtils.unescapeJava(StringUtils.strip(context.getMatch(), "\""));
	}

	Rule DescriptionDeclaration() {
		return Sequence(
				Ch(','),
				Whitespace(),
				StringLiteral(),
				runAction(this::setDescription),
				Whitespace()
				).label("Description declaration");
	}
	
	static void setTechnology(Context context) {
		String label = getCleanedStringLiteralMatch(context);
		((WithTechnology) context.getValueStack().peek()).withTechnology(label);
	}

	Rule TechnologyDeclaration() {
		return Sequence(
				Ch(','),
				Whitespace(),
				StringLiteral(),
				runAction(C4PlantUMLParser::setTechnology),
				Whitespace()
				).label("Technology declaration");
	}
	
	void setLabel(Context context) {
		String label = getCleanedStringLiteralMatch(context);
		((WithLabel) context.getValueStack().peek()).withLabel(label);
	}

	Rule LabelDeclaration() {
		return Sequence(
				Ch(','),
				Whitespace(),
				StringLiteral(),
				runAction(this::setLabel),
				Whitespace()
				).label("Label declaration");
	}
	
	void setAlias(Context context) {
		String alias = context.getMatch();
		((WithAlias) context.getValueStack().peek()).withAlias(alias);
	}

	Rule AliasDeclaration() {
		return AliasDeclaration(this::setAlias);
	}
	Rule AliasDeclaration(Consumer<Context> setAlias) {
		return Sequence(
				Whitespace(),
				Identifier(),
				runAction(setAlias),
				Whitespace()
				).label("Alias declaration");
	}

	// Will be changed to separate handling of local vs remote includes
	public Rule Include() {
        Var<String> included = new Var<String>(); // we use an action variable to hold the operator character
		return Sequence(
				Whitespace(),
				String("!include "),
				Whitespace(),
				Optional(Ch('"')),
				ZeroOrMore(NoneOf("\n")),
				included.set(match()),
				Optional(Ch('"')),
				String("\n"),
				((WorkspaceElementBuilder) peek()).add(new Include(included.get()))
				)
				.label("!include ...");
	}

	@Override
    protected Rule fromStringLiteral(String string) {
        return string.endsWith(" ") ?
                Sequence(String(string.substring(0, string.length() - 1)), Whitespace()) :
                String(string);
    }
    
    //////////////////////////////////////////////////////////////
    ////// Borrowed from Java interpreter Parboiled example //////
    //////////////////////////////////////////////////////////////


    Rule Escape() {
        return Sequence('\\', AnyOf("btnfr\"\'\\"));
    }
    
    Rule StringLiteral() {
        return Sequence(
                Ch('"'),
                ZeroOrMore(
                        FirstOf(
                                Escape(),
                                Sequence(TestNot(AnyOf("\r\n\"\\")), ANY)
                        )
                ),
                Ch('"')
        ).suppressSubnodes();
    }
    
    /**
     * Matches an identifier and output identifier text
     * @return
     */
    @SuppressSubnodes
    @MemoMismatches
    Rule Identifier() {
        return Sequence(
        		Letter(),
        		ZeroOrMore(
        				LetterOrDigit()
        				), 
        		Whitespace()
        		).label("Identifier");
    }

    // JLS defines letters and digits as Unicode characters recognized
    // as such by special Java procedures.

    Rule Letter() {
        // switch to this "reduced" character space version for a ~10% parser performance speedup
        //return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), '_', '$');
        return new JavaLetterMatcher();
    }

    @MemoMismatches
    Rule LetterOrDigit() {
        return new JavaLetterOrDigitMatcher();
    }


    @SuppressNode
    Rule Whitespace() {
        return ZeroOrMore(FirstOf(

                // whitespace
                OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

                // traditional comment
                Sequence("/'", ZeroOrMore(TestNot("'/"), ANY), "*/")
                	.label("Traditionnal comment"),

                // end of line comment
                Sequence(
                        '\'',
                        ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
                        FirstOf("\r\n", '\r', '\n', EOI)
                        .label("End of line comment")
                )
        ));
    }

	Action runAction(Consumer<Context> consumer) {
		return new Action() {

			@Override
			public boolean run(Context context) {
				consumer.accept(context);
				return true;
			}
		};
	}
	
	Action runAction(Function<Context, Boolean> consumer) {
		return new Action() {

			@Override
			public boolean run(Context context) {
				return consumer.apply(context);
			}
		};
	}
	
}
