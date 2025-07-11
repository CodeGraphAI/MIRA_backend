package MuskElion.CodeGraph.graph.node;

import MuskElion.CodeGraph.graph.relationship.CallsRelationship;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * Neo4j에 저장될 함수 또는 메서드 노드를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@Setter
@Getter
@Node("Function")
@EqualsAndHashCode(of = "id")
public class FunctionNode {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 함수의 이름입니다.
     */
    @Property("name")
    private String name;

    /**
     * 함수가 정의된 파일의 경로입니다.
     */
    @Property("file_path")
    private String filePath;

    /**
     * 함수 정의가 시작되는 라인 번호입니다.
     */
    @Property("start_line")
    private int startLine;

    /**
     * 함수 정의가 끝나는 라인 번호입니다.
     */
    @Property("end_line")
    private int endLine;

    /**
     * 이 함수가 호출하는 다른 함수와의 관계(CALLS) 리스트입니다.
     */
    @Relationship(type = "CALLS", direction = Relationship.Direction.OUTGOING)
    private List<CallsRelationship> calls;
}
