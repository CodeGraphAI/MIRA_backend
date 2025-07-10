package MuskElion.CodeGraph.graph.repository;

import MuskElion.CodeGraph.graph.node.ModuleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ModuleNode 엔티티에 대한 데이터 접근을 처리하는 리포지토리 인터페이스입니다.
 */
@Repository
public interface ModuleNodeRepository extends Neo4jRepository<ModuleNode, Long> {
    /**
     * 파일 경로를 기준으로 ModuleNode를 찾습니다.
     * @param filePath 파일 경로
     * @return Optional<ModuleNode>
     */
    Optional<ModuleNode> findByFilePath(String filePath);

    /**
     * 모듈 이름을 기준으로 ModuleNode를 찾습니다.
     * @param name 모듈 이름
     * @return Optional<ModuleNode>
     */
    Optional<ModuleNode> findByName(String name);
}