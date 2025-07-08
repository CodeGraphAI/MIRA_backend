package MuskElion.CodeGraph.parser.controller;

import MuskElion.CodeGraph.parser.dto.ParseResult;
import MuskElion.CodeGraph.parser.service.ParserService;
import org.springframework.http.HttpStatus; // HttpStatus import 추가
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parser")
public class ParserController {

    private final ParserService parserService;

    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/parse")
    public ResponseEntity<String> receiveParseResult(@RequestBody ParseResult parseResult) {
        // Basic validation (can be enhanced with @Valid and custom validators)
        if (parseResult == null || parseResult.getFilePath() == null || parseResult.getLanguage() == null || parseResult.getRootNode() == null) {
            return new ResponseEntity<>("Invalid parse result: filePath, language, and rootNode are required.", HttpStatus.BAD_REQUEST);
        }

        boolean success = parserService.processParseResult(parseResult);

        if (success) {
            return new ResponseEntity<>("Parse result for '" + parseResult.getFilePath() + "' received and processed successfully.", HttpStatus.OK);
        } else {
            // In a real application, you might return more specific error details
            return new ResponseEntity<>("Failed to process parse result for '" + parseResult.getFilePath() + "'. See server logs for details.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
