package MuskElion.CodeGraph.graph.node;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * Neo4j에 저장될 모듈(파일) 노드를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@Node("Module")
public class ModuleNode {

    @Id
    @GeneratedValue
    private String id;

    /**
     * 모듈(파일)의 절대 경로입니다.
     */
    @Property("file_path")
    private String filePath;

    /**
     * 코드의 프로그래밍 언어입니다.
     */
    @Property("language")
    private String language;

    /**
     * 모듈(파일)의 이름입니다.
     */
    @Property("name")
    private String name;

    /**
     * 이 모듈에 정의된 클래스 노드들의 리스트입니다.
     */
    @Relationship(type = "DEFINES_CLASS", direction = Relationship.Direction.OUTGOING)
    private List<ClassNode> definedClasses;

    /**
     * 이 모듈에 정의된 함수 노드들의 리스트입니다.
     */
    @Relationship(type = "DEFINES_FUNCTION", direction = Relationship.Direction.OUTGOING)
    private List<FunctionNode> definedFunctions;
}
