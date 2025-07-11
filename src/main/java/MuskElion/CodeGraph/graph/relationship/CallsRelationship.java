package MuskElion.CodeGraph.graph.relationship;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 함수 호출 관계(CALLS)를 나타내는 엔티티 클래스입니다.
 * 이 관계는 하나의 함수가 다른 함수를 호출하는 것을 표현합니다.
 */
@Data
@Builder
@RelationshipProperties
public class CallsRelationship {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 호출되는 함수(타겟 노드)입니다.
     */
    @TargetNode
    private FunctionNode calledFunction;

    /**
     * 함수가 호출되는 소스 코드의 라인 번호입니다.
     */
    @Property("call_site_line")
    private int callSiteLine;
}
