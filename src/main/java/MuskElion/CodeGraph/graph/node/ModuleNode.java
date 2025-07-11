package MuskElion.CodeGraph.graph.node;

import MuskElion.CodeGraph.graph.relationship.ImportsRelationship;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Neo4j에 저장될 모듈(파일) 노드를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@Setter
@Getter
@Node("Module")
@EqualsAndHashCode(of = "id")
public class ModuleNode {

    @Id
    @GeneratedValue
    private Long id;

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

    /**
     * 이 모듈이 임포트하는 다른 모듈과의 관계 리스트입니다.
     */
    @Relationship(type = "IMPORTS", direction = Relationship.Direction.OUTGOING)
    private List<ImportsRelationship> imports;

    public void addDefinedClass(ClassNode classNode) {
        if (this.definedClasses == null) {
            this.definedClasses = new ArrayList<>();
        }
        if (!this.definedClasses.contains(classNode)) {
            this.definedClasses.add(classNode);
        }
    }

    public void addDefinedFunction(FunctionNode functionNode) {
        if (this.definedFunctions == null) {
            this.definedFunctions = new ArrayList<>();
        }
        if (!this.definedFunctions.contains(functionNode)) {
            this.definedFunctions.add(functionNode);
        }
    }
}
