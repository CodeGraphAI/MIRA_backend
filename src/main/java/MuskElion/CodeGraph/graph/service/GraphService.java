package MuskElion.CodeGraph.graph.service;

import MuskElion.CodeGraph.parser.dto.ParseResult;

/**
 * 파싱 결과를 그래프에 저장하는 서비스 인터페이스.
 */
public interface GraphService {
    void saveParsedResult(ParseResult parseResult);
}
