package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.repository.ClassNodeRepository;
import MuskElion.CodeGraph.parser.dto.AstNode;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 클래스 노드를 처리하는 구현체.
 */
@Component
public class ClassProcessorImpl implements ClassProcessor {

    private final ClassNodeRepository classNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public ClassProcessorImpl(ClassNodeRepository classNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.classNodeRepository = classNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public ClassNode findOrCreate(AstNode classAstNode, String filePath) {
        String className = astToGraphMapper.extractNameFromAstNode(classAstNode);
        if (className == null) return null;

        int startLine = classAstNode.getStartPosition().getRow();
        int endLine = classAstNode.getEndPosition().getRow();

        Optional<ClassNode> existingClass = classNodeRepository.findByNameAndFilePath(className, filePath);
        ClassNode classNode;
        if (existingClass.isPresent()) {
            classNode = existingClass.get();
            if (classNode.getStartLine() != startLine || classNode.getEndLine() != endLine) {
                classNode.setStartLine(startLine);
                classNode.setEndLine(endLine);
                classNodeRepository.save(classNode);
            }
        } else {
            classNode = astToGraphMapper.mapToClassNode(classAstNode, filePath);
            classNodeRepository.save(classNode);
        }
        return classNode;
    }

    @Override
    public ClassNode save(ClassNode classNode) {
        return classNodeRepository.save(classNode);
    }
}
