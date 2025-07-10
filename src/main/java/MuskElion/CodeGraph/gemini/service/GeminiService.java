package MuskElion.CodeGraph.gemini.service;

import MuskElion.CodeGraph.gemini.dto.GeminiRequestDTO;
import MuskElion.CodeGraph.gemini.dto.GeminiResponseDTO;
import MuskElion.CodeGraph.gemini.dto.TextPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeminiService {
    public static final String GEMINI_PRO = "gemini-pro";
    public static final String GEMINI_ULTIMATE = "gemini-ultimate";
    public static final String GEMINI_PRO_VISION = "gemini-pro-vision";

    private final GeminiInterface geminiInterface;

    @Autowired
    public GeminiService(GeminiInterface geminiInterface) {
        this.geminiInterface = geminiInterface;
    }

    private GeminiResponseDTO getCompletion(GeminiRequestDTO request){
        return geminiInterface.getCompletion(GEMINI_PRO,request);
    }

    public String getCompletion(String text){
        GeminiRequestDTO request = new GeminiRequestDTO(text);
        GeminiResponseDTO response = getCompletion(request);

        return response.getCandidates()
                .stream()
                .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                        .stream()
                        .findFirst()
                        .map(part -> Optional.ofNullable(((TextPart)part).getText()).orElse(null)))
                .orElse(null);
    }
}
