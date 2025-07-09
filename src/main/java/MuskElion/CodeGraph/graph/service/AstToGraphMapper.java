package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.graph.relationship.CallsRelationship;
import MuskElion.CodeGraph.graph.relationship.ImportsRelationship;
import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import MuskElion.CodeGraph.parser.dto.AstNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;

/**
 * AST 노드를 그래프 노드 및 관계로 매핑하는 인터페이스.
 */
public interface AstToGraphMapper {

    ModuleNode mapToModuleNode(ParseResult parseResult);

    ClassNode mapToClassNode(AstNode classAstNode, String filePath);

    FunctionNode mapToFunctionNode(AstNode functionAstNode, String filePath);

    ImportsRelationship mapToImportRelationship(AstNode importAstNode, ModuleNode sourceModule, String filePath);

    InheritsRelationship mapToInheritRelationship(AstNode superclassAstNode, ClassNode childClass, String filePath);

    CallsRelationship mapToCallRelationship(AstNode callAstNode, FunctionNode callingFunction, String filePath);

    String extractNameFromAstNode(AstNode astNode);
}
