package io.github.riduidel.structurizr.visitor;

import java.util.List;

public interface GroupAwareVisitor extends Visitor {

	boolean startVisitGroup(List<String> nextPath);

	void endVisitGroup(List<String> nextPath);

}
