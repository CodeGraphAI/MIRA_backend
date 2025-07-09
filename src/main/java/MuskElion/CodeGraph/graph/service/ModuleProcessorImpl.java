package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.graph.repository.ModuleNodeRepository;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 모듈 노드를 처리하는 구현체.
 */
@Component
public class ModuleProcessorImpl implements ModuleProcessor {

    private final ModuleNodeRepository moduleNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public ModuleProcessorImpl(ModuleNodeRepository moduleNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.moduleNodeRepository = moduleNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public ModuleNode findOrCreate(ParseResult parseResult) {
        Optional<ModuleNode> existingModule = moduleNodeRepository.findByFilePath(parseResult.getFilePath());
        ModuleNode moduleNode;
        if (existingModule.isPresent()) {
            moduleNode = existingModule.get();
            if (!moduleNode.getLanguage().equals(parseResult.getLanguage())) {
                moduleNode.setLanguage(parseResult.getLanguage());
                moduleNodeRepository.save(moduleNode);
            }
        } else {
            moduleNode = astToGraphMapper.mapToModuleNode(parseResult); // Use mapper
            moduleNodeRepository.save(moduleNode);
        }
        return moduleNode;
    }

    @Override
    public ModuleNode save(ModuleNode moduleNode) {
        return moduleNodeRepository.save(moduleNode);
    }
}
