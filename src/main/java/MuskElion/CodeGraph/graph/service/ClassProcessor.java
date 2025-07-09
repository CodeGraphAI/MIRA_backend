package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * 클래스 노드를 처리하는 인터페이스.
 */
public interface ClassProcessor {
    ClassNode findOrCreate(AstNode classAstNode, String filePath);
    ClassNode save(ClassNode classNode);
}
