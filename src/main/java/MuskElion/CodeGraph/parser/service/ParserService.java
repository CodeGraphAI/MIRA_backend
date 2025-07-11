package MuskElion.CodeGraph.parser.service;

import MuskElion.CodeGraph.graph.service.GraphService;
import MuskElion.CodeGraph.parser.dto.ParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParserService {

    private static final Logger logger = LoggerFactory.getLogger(ParserService.class);
    private final GraphService graphService;

    public ParserService(GraphService graphService) {
        this.graphService = graphService;
    }

    public boolean processParseResult(ParseResult parseResult) {
        try {
            logger.info("Processing parse result for file: {}", parseResult.getFilePath());
            graphService.saveParsedResult(parseResult);
            logger.info("Successfully processed parse result for file: {}", parseResult.getFilePath());
            return true;
        } catch (Exception e) {
            logger.error("Error processing parse result for file {}: ", parseResult.getFilePath(), e);
            return false; // Indicate failure
        }
    }
}
