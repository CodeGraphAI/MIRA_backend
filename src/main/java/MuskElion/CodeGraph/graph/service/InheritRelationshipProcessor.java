package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * 상속 관계를 처리하는 인터페이스.
 */
public interface InheritRelationshipProcessor {
    void process(AstNode superclassAstNode, ClassNode childClass, String filePath);
}
