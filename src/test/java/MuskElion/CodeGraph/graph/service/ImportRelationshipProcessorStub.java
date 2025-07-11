package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * ImportRelationshipProcessor 스텁 클래스.
 */
class ImportRelationshipProcessorStub implements ImportRelationshipProcessor {
    public boolean processCalled = false;
    public void process(AstNode astNode, ModuleNode moduleNode, String filePath) {
        this.processCalled = true;
    }
}