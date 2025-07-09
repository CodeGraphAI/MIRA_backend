package MuskElion.CodeGraph.graph.relationship;

import MuskElion.CodeGraph.graph.node.ClassNode;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 클래스 상속 관계(INHERITS)를 나타내는 엔티티 클래스입니다.
 */
@Data
@Builder
@RelationshipProperties
public class InheritsRelationship {

    @Id
    @GeneratedValue
    private String id;

    /**
     * 상속하는 부모 클래스(타겟 노드)입니다.
     */
    @TargetNode
    private ClassNode parentClass;
}
