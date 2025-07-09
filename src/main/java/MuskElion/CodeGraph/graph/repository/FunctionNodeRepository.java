package MuskElion.CodeGraph.graph.repository;

import MuskElion.CodeGraph.graph.node.FunctionNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * FunctionNode 엔티티에 대한 데이터 접근을 처리하는 리포지토리 인터페이스입니다.
 */
@Repository
public interface FunctionNodeRepository extends Neo4jRepository<FunctionNode, String> {
    /**
     * 함수 이름, 파일 경로, 시작 라인 번호를 기준으로 FunctionNode를 찾습니다.
     * @param name 함수 이름
     * @param filePath 파일 경로
     * @param startLine 시작 라인 번호
     * @return Optional<FunctionNode>
     */
    Optional<FunctionNode> findByNameAndFilePathAndStartLine(String name, String filePath, int startLine);
}
