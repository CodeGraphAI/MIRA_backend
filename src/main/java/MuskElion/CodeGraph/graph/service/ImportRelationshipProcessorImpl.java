package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.graph.relationship.ImportsRelationship;
import MuskElion.CodeGraph.graph.repository.ModuleNodeRepository;
import MuskElion.CodeGraph.parser.dto.AstNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 임포트 관계를 처리하는 구현체.
 */
@Component
public class ImportRelationshipProcessorImpl implements ImportRelationshipProcessor {

    private final ModuleNodeRepository moduleNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public ImportRelationshipProcessorImpl(ModuleNodeRepository moduleNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.moduleNodeRepository = moduleNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public void process(AstNode importAstNode, ModuleNode sourceModule, String filePath) {
        String importedName = astToGraphMapper.extractNameFromAstNode(importAstNode);
        if (importedName == null) return;

        Optional<ModuleNode> targetModuleOpt = moduleNodeRepository.findByName(importedName);
        ModuleNode targetModule;
        if (targetModuleOpt.isPresent()) {
            targetModule = targetModuleOpt.get();
        } else {
            targetModule = ModuleNode.builder()
                    .id(java.util.UUID.randomUUID().toString())
                    .name(importedName)
                    .build();
            moduleNodeRepository.save(targetModule);
        }

        ImportsRelationship importsRelationship = astToGraphMapper.mapToImportRelationship(importAstNode, sourceModule, filePath);
        importsRelationship.setImportedModule(targetModule);

        if (sourceModule.getImports() == null) {
            sourceModule.setImports(new ArrayList<>());
        }
        boolean importExists = false;
        for (ImportsRelationship existingImport : sourceModule.getImports()) {
            if (existingImport.getImportName().equals(importsRelationship.getImportName()) &&
                existingImport.getImportedModule().equals(importsRelationship.getImportedModule())) {
                importExists = true;
                break;
            }
        }
        if (!importExists) {
            sourceModule.getImports().add(importsRelationship);
            moduleNodeRepository.save(sourceModule);
        }
    }
}
