package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * 함수 노드를 처리하는 인터페이스.
 */
public interface FunctionProcessor {
    FunctionNode findOrCreate(AstNode functionAstNode, String filePath);
    FunctionNode save(FunctionNode functionNode);
}
