package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import MuskElion.CodeGraph.parser.dto.ParseResult;

public interface ModuleProcessor {
    ModuleNode findOrCreate(ParseResult parseResult);
    ModuleNode save(ModuleNode moduleNode);
}
