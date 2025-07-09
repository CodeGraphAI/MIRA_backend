package MuskElion.CodeGraph.graph.relationship;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 모듈 임포트 관계(IMPORTS)를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@RelationshipProperties
public class ImportsRelationship {

    @Id
    @GeneratedValue
    private String id;

    /**
     * 임포트되는 모듈(타겟 노드)입니다.
     */
    @TargetNode
    private ModuleNode importedModule;

    /**
     * 임포트 구문에서 사용된 이름입니다. (예: "java.util.List")
     */
    @Property("import_name")
    private String importName; // e.g., "java.util.List"

    /**
     * 임포트 구문이 위치한 라인 번호입니다.
     */
    @Property("line_number")
    private int lineNumber;
}
