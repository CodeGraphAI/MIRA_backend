package MuskElion.CodeGraph.graph.repository;

import MuskElion.CodeGraph.graph.node.ClassNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassNode 엔티티에 대한 데이터 접근을 처리하는 리포지토리 인터페이스입니다.
 */
@Repository
public interface ClassNodeRepository extends Neo4jRepository<ClassNode, String> {
    /**
     * 클래스 이름과 파일 경로를 기준으로 ClassNode를 찾습니다.
     * @param name 클래스 이름
     * @param filePath 파일 경로
     * @return Optional<ClassNode>
     */
    Optional<ClassNode> findByNameAndFilePath(String name, String filePath);
}