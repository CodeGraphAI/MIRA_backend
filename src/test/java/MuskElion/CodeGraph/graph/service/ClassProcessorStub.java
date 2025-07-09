package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.parser.dto.AstNode;

import java.util.ArrayList;
import java.util.UUID;

/**
 * ClassProcessor 스텁 클래스.
 */
class ClassProcessorStub implements ClassProcessor {
    public ClassNode lastSavedClass;
    public ClassNode findOrCreate(AstNode astNode, String filePath) {
        ClassNode classNode = ClassNode.builder()
                .id(UUID.randomUUID().toString())
                .name(astNode.getValue()) // Assuming value is the name for simplicity
                .filePath(filePath)
                .startLine(astNode.getStartPosition().getRow())
                .endLine(astNode.getEndPosition().getRow())
                .definedFunctions(new ArrayList<>())
                .build();
        return classNode;
    }
    public ClassNode save(ClassNode classNode) {
        this.lastSavedClass = classNode;
        return classNode;
    }
}