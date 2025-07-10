package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;

import java.util.ArrayList;
import java.util.UUID;

/**
 * ModuleProcessor 스텁 클래스.
 */
class ModuleProcessorStub implements ModuleProcessor {
    public ModuleNode lastSavedModule;
    public ModuleNode findOrCreate(ParseResult parseResult) {
        return ModuleNode.builder()
                
                .filePath(parseResult.getFilePath())
                .language(parseResult.getLanguage())
                .name(parseResult.getFilePath().substring(parseResult.getFilePath().lastIndexOf('/') + 1, parseResult.getFilePath().lastIndexOf('.')))
                .definedClasses(new ArrayList<>())
                .definedFunctions(new ArrayList<>())
                .build();
    }
    public ModuleNode save(ModuleNode moduleNode) {
        this.lastSavedModule = moduleNode;
        return moduleNode;
    }
}