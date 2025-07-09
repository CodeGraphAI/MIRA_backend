package MuskElion.CodeGraph.graph.node;

import MuskElion.CodeGraph.graph.relationship.InheritsRelationship;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;
import java.util.ArrayList;

/** 클래스 노드 */
@Data
@Builder
@Setter
@Getter
@Node("Class")
@EqualsAndHashCode(of = "id")
public class ClassNode {

    @Id
    @GeneratedValue
    private String id; // ID

    @Property("name")
    private String name; // 이름

    @Property("file_path")
    private String filePath; // 파일 경로

    @Property("start_line")
    private int startLine; // 시작 라인

    @Property("end_line")
    private int endLine; // 끝 라인

    @Relationship(type = "INHERITS", direction = Relationship.Direction.OUTGOING)
    private List<InheritsRelationship> inherits; // 상속 관계

    @Relationship(type = "DEFINES_FUNCTION", direction = Relationship.Direction.OUTGOING)
    private List<FunctionNode> definedFunctions; // 정의된 함수 목록

    // 함수 추가
    public void addDefinedFunction(FunctionNode functionNode) {
        if (this.definedFunctions == null) {
            this.definedFunctions = new ArrayList<>();
        }
        if (!this.definedFunctions.contains(functionNode)) {
            this.definedFunctions.add(functionNode);
        }
    }
}
