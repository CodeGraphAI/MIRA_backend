package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.graph.relationship.ImportsRelationship;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * 임포트 관계를 처리하는 인터페이스.
 */
public interface ImportRelationshipProcessor {
    void process(AstNode importAstNode, ModuleNode sourceModule, String filePath);
}
