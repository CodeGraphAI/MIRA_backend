package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import MuskElion.CodeGraph.graph.relationship.CallsRelationship;
import MuskElion.CodeGraph.graph.repository.FunctionNodeRepository;
import MuskElion.CodeGraph.parser.dto.AstNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 호출 관계를 처리하는 구현체.
 */
@Component
public class CallRelationshipProcessorImpl implements CallRelationshipProcessor {

    private final FunctionNodeRepository functionNodeRepository;
    private final AstToGraphMapper astToGraphMapper;

    public CallRelationshipProcessorImpl(FunctionNodeRepository functionNodeRepository, AstToGraphMapper astToGraphMapper) {
        this.functionNodeRepository = functionNodeRepository;
        this.astToGraphMapper = astToGraphMapper;
    }

    @Override
    public void process(AstNode callAstNode, FunctionNode callingFunction, String filePath) {
        String calledFunctionName = astToGraphMapper.extractNameFromAstNode(callAstNode);
        if (calledFunctionName == null) return;
        int callSiteLine = callAstNode.getStartPosition().getRow();

        Optional<FunctionNode> calledFunctionOpt = functionNodeRepository.findByNameAndFilePathAndStartLine(calledFunctionName, filePath, callSiteLine);
        FunctionNode calledFunction;
        if (calledFunctionOpt.isPresent()) {
            calledFunction = calledFunctionOpt.get();
        } else {
            calledFunction = FunctionNode.builder()
                    .id(java.util.UUID.randomUUID().toString())
                    .name(calledFunctionName)
                    .build();
            functionNodeRepository.save(calledFunction);
        }

        CallsRelationship callsRelationship = astToGraphMapper.mapToCallRelationship(callAstNode, callingFunction, filePath);
        callsRelationship.setCalledFunction(calledFunction);

        if (callingFunction.getCalls() == null) {
            callingFunction.setCalls(new ArrayList<>());
        }
        boolean callExists = false;
        for (CallsRelationship existingCall : callingFunction.getCalls()) {
            if (existingCall.getCalledFunction().equals(callsRelationship.getCalledFunction()) &&
                existingCall.getCallSiteLine() == callsRelationship.getCallSiteLine()) {
                callExists = true;
                break;
            }
        }
        if (!callExists) {
            callingFunction.getCalls().add(callsRelationship);
            functionNodeRepository.save(callingFunction);
        }
    }
}
