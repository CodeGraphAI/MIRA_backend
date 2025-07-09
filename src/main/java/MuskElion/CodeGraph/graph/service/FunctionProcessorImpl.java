package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.repository.FunctionNodeRepository;
import MuskElion.CodeGraph.parser.dto.AstNode;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 함수 노드를 처리하는 구현체.
 */
@Component
public class FunctionProcessorImpl implements FunctionProcessor {

    private final FunctionNodeRepository functionNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public FunctionProcessorImpl(FunctionNodeRepository functionNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.functionNodeRepository = functionNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public FunctionNode findOrCreate(AstNode functionAstNode, String filePath) {
        String functionName = astToGraphMapper.extractNameFromAstNode(functionAstNode);
        if (functionName == null) return null;

        int startLine = functionAstNode.getStartPosition().getRow();
        int endLine = functionAstNode.getEndPosition().getRow();

        Optional<FunctionNode> existingFunction = functionNodeRepository.findByNameAndFilePathAndStartLine(functionName, filePath, startLine);
        FunctionNode functionNode;
        if (existingFunction.isPresent()) {
            functionNode = existingFunction.get();
            if (functionNode.getEndLine() != endLine) {
                functionNode.setEndLine(endLine);
                functionNodeRepository.save(functionNode);
            }
        } else {
            functionNode = astToGraphMapper.mapToFunctionNode(functionAstNode, filePath); // Use mapper
            functionNodeRepository.save(functionNode);
        }
        return functionNode;
    }

    @Override
    public FunctionNode save(FunctionNode functionNode) {
        return functionNodeRepository.save(functionNode);
    }
}
