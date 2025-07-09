package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.relationship.CallsRelationship;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * 호출 관계를 처리하는 인터페이스.
 */
public interface CallRelationshipProcessor {
    void process(AstNode callAstNode, FunctionNode callingFunction, String filePath);
}
