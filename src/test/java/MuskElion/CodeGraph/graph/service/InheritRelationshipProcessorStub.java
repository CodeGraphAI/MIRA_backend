package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

/**
 * InheritRelationshipProcessor 스텁 클래스.
 */
class InheritRelationshipProcessorStub implements InheritRelationshipProcessor {
    public boolean processCalled = false;
    public void process(AstNode astNode, ClassNode classNode, String filePath) {
        this.processCalled = true;
        System.out.println("TestInheritRelationshipProcessor: process called for " + astNode.getValue() + " by " + classNode.getName());
    }
}