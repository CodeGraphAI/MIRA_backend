package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ClassNode;
import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import MuskElion.CodeGraph.graph.repository.ClassNodeRepository;
import MuskElion.CodeGraph.parser.dto.AstNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 상속 관계를 처리하는 구현체.
 */
@Component
public class InheritRelationshipProcessorImpl implements InheritRelationshipProcessor {

    private final ClassNodeRepository classNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public InheritRelationshipProcessorImpl(ClassNodeRepository classNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.classNodeRepository = classNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public void process(AstNode superclassAstNode, ClassNode childClass, String filePath) {
        String parentClassName = astToGraphMapper.extractNameFromAstNode(superclassAstNode);
        if (parentClassName == null) return;

        Optional<ClassNode> parentClassOpt = classNodeRepository.findByNameAndFilePath(parentClassName, filePath);
        ClassNode parentClass;
        if (parentClassOpt.isPresent()) {
            parentClass = parentClassOpt.get();
        } else {
            parentClass = ClassNode.builder()
                    .id(java.util.UUID.randomUUID().toString())
                    .name(parentClassName)
                    .build();
            classNodeRepository.save(parentClass);
        }

        InheritsRelationship inheritsRelationship = astToGraphMapper.mapToInheritRelationship(superclassAstNode, childClass, filePath);
        inheritsRelationship.setParentClass(parentClass);

        if (childClass.getInherits() == null) {
            childClass.setInherits(new ArrayList<>());
        }
        boolean inheritsExists = false;
        for (InheritsRelationship existingInherits : childClass.getInherits()) {
            if (existingInherits.getParentClass().equals(inheritsRelationship.getParentClass())) {
                inheritsExists = true;
                break;
            }
        }
        if (!inheritsExists) {
            childClass.getInherits().add(inheritsRelationship);
            classNodeRepository.save(childClass);
        }
    }
}
