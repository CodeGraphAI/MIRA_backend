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
 * AstToGraphMapper 스텁 클래스.
 */
class AstToGraphMapperStub implements AstToGraphMapper {
    public String extractNameFromAstNode(AstNode astNode) {
        if (astNode == null) return null;
        if (astNode.getValue() != null) return astNode.getValue();
        return astNode.getType(); // Fallback
    }
    public ModuleNode mapToModuleNode(ParseResult parseResult) { return null; }
    public ClassNode mapToClassNode(AstNode astNode, String filePath) { return null; }
    public FunctionNode mapToFunctionNode(AstNode astNode, String filePath) { return null; }
    public ImportsRelationship mapToImportRelationship(AstNode astNode, ModuleNode moduleNode, String filePath) { return null; }
    public InheritsRelationship mapToInheritRelationship(AstNode astNode, ClassNode classNode, String filePath) { return null; }
    public CallsRelationship mapToCallRelationship(AstNode astNode, FunctionNode functionNode, String filePath) { return null; }
}