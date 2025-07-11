package MuskElion.CodeGraph.parser.service;

import MuskElion.CodeGraph.graph.service.GraphService;
import MuskElion.CodeGraph.parser.dto.ParseResult;

/**
 * ParserService 스텁 클래스.
 */
public class ParserServiceStub extends ParserService {
    public boolean processResultToReturn = true;

    public ParserServiceStub(GraphService graphService) {
        super(graphService);
    }

    @Override
    public boolean processParseResult(ParseResult parseResult) {
        return processResultToReturn;
    }
}