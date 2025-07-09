package MuskElion.CodeGraph.graph.node;

import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * Neo4j에 저장될 클래스 노드를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@Node("Class")
public class ClassNode {

    @Id
    @GeneratedValue
    private String id;

    /**
     * 클래스의 이름입니다.
     */
    @Property("name")
    private String name;

    /**
     * 클래스가 정의된 파일의 경로입니다.
     */
    @Property("file_path")
    private String filePath;

    /**
     * 클래스 정의가 시작되는 라인 번호입니다.
     */
    @Property("start_line")
    private int startLine;

    /**
     * 클래스 정의가 끝나는 라인 번호입니다.
     */
    @Property("end_line")
    private int endLine;

    /**
     * 이 클래스가 상속하는 다른 클래스와의 관계 리스트입니다.
     */
    @Relationship(type = "INHERITS", direction = Relationship.Direction.OUTGOING)
    private List<InheritsRelationship> inherits;

    /**
     * 이 클래스 내에 정의된 함수(메서드) 노드들의 리스트입니다.
     */
    @Relationship(type = "DEFINES_FUNCTION", direction = Relationship.Direction.OUTGOING)
    private List<FunctionNode> definedFunctions;
}
