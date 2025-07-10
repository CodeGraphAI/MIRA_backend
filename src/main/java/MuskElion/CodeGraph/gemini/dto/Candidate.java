package MuskElion.CodeGraph.gemini.dto;

import lombok.Getter;


/**
 * 모델의 생성 결과 한 건을 나타내는 DTO
 * content : 실제 응답 내용
 * index : 결과 순번
 * finishReason : 완료 사유
 * */
@Getter
public class Candidate {
    private Content content;
    private String finishReason;
    private int index;
}
