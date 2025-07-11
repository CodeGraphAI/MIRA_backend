package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

import java.util.UUID;

/**
 * FunctionProcessor 스텁 클래스.
 */
class FunctionProcessorStub implements FunctionProcessor {
    public FunctionNode lastSavedFunction;
    public FunctionNode findOrCreate(AstNode astNode, String filePath) {
        return FunctionNode.builder()
                
                .name(astNode.getValue())
                .filePath(filePath)
                .startLine(astNode.getStartPosition().getRow())
                .endLine(astNode.getEndPosition().getRow())
                .build();
    }
    public FunctionNode save(FunctionNode functionNode) {
        this.lastSavedFunction = functionNode;
        return functionNode;
    }
}