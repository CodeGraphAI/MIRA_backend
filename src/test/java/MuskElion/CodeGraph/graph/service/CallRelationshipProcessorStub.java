package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * CallRelationshipProcessor 스텁 클래스.
 */
class CallRelationshipProcessorStub implements CallRelationshipProcessor {
    public boolean processCalled = false;
    public void process(AstNode astNode, FunctionNode functionNode, String filePath) {
        this.processCalled = true;
        System.out.println("TestCallRelationshipProcessor: process called for " + astNode.getValue() + " by " + functionNode.getName());
    }
}