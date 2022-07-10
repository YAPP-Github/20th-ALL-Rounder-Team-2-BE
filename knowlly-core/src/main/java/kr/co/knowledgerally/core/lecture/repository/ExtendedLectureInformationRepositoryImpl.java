package kr.co.knowledgerally.core.lecture.repository;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.lecture.entity.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 확장된 lecture-info repository
 */
@Repository
@RequiredArgsConstructor
public class ExtendedLectureInformationRepositoryImpl implements ExtendedLectureInformationRepository {
    private final EntityManager entityManager;
    private final LectureRepository lectureRepository;

    /*
     * Q1. 왜 아래와 같은 복잡한 로직이 작성되었는가?
     * A1. N+1 문제를 해소하기 위함. 이때 fetch join을 List 자료형에 대해 두 번 사용하게 되면 MultipleBagFetchException이라는 녀석이 생긴다.
     * 문제가 됬던 Entity는 LectureInfo 안 List<Lecture>, 그리고 Lecture 안 List<Form>
     * (결과적으로 아래 로직은 쿼리를 총 2번 생성한다.)
     * JPQL 만으로는 이 문제를 해결하기가 어려워서, 자바의 힘을 빌렸다.
     *
     * Q2. List 대신 Set을 사용하면 위 문제가 해결되는데, Set을 사용 안한 이유?
     * A2. 순서가 중요했다. 생성 시간 기준 내림차순으로 정렬해서 주고 싶어서...
     */
    /**
     * 코치로 모든 클래스-info와 연관 정보들을 찾습니다.
     * @param coach 코치 객체
     * @return 클래스-info 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<LectureInformation> findAllByCoach(Coach coach) {
        // Step 1. 코치 기준으로 lectures 를 찾는다.
        List<Lecture> lectures = lectureRepository.findAllByCoach(coach);

        // Step 2. 찾은 lectures를 lecture-info id 기준으로 해시맵에 매핑한다.
        Map<Long, List<Lecture>> lectureInformationMap = mapify(lectures);

        // Step 3. 코치 기준으로 lecture-info 및 연관 정보를 찾는다. 단, lecture 정보는 select 대상에서 제외한다.
        List<LectureInformation> lectureInformations = entityManager.createQuery(
            "select distinct li from LectureInformation li " +
                    "join fetch li.coach co join fetch co.user " +
                    "join fetch li.category " +
                    "left join fetch li.lectureImages lim " +
                    "left join fetch li.tags t " +
                    "where li.coach = :coach and li.isActive = true " +
                    "and (lim.isActive = true or lim.isActive is null) " +
                    "and (t.isActive = true or t.isActive is null) " +
                    "order by li.createdAt desc", LectureInformation.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .setParameter("coach", coach)
                .getResultList();

        // Step 4. 찾은 lecture-info 목록을 돌면서 Step 2 에서 매핑해둔 lectures 정보를 set 한다.
        for (LectureInformation lectureInformation : lectureInformations) {
            lectureInformation.setLectures(lectureInformationMap.getOrDefault(lectureInformation.getId(),
                    Collections.emptyList()));
        }

        return lectureInformations;
    }

    /**
     * 코치와 클래스 상태로 모든 클래스-info와 연관 정보들을 찾습니다.
     * @param coach 코치 객체
     * @param state 상태 enum 값
     * @return 클래스-info 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<LectureInformation> findAllByCoachAndState(Coach coach, Lecture.State state) {
        // Step 1. 코치와 상태 기준으로 lectures 를 찾는다.
        List<Lecture> lectures = lectureRepository.findAllByCoachAndState(coach, state);

        // Step 2. 찾은 lectures를 lecture-info id 기준으로 해시맵에 매핑한다.
        Map<Long, List<Lecture>> lectureInformationMap = mapify(lectures);

        // Step 3. 코치 기준으로 lecture-info 및 연관 정보를 찾는다. 단, lecture 정보는 select 대상에서 제외한다.
        List<LectureInformation> lectureInformations = entityManager.createQuery(
                        "select distinct li from LectureInformation li " +
                                "join fetch li.coach co join fetch co.user " +
                                "join fetch li.category " +
                                "left join fetch li.lectureImages lim " +
                                "left join fetch li.tags t " +
                                "left join li.lectures lec " +
                                "where li.coach = :coach " +
                                "and lec.state = :state " +
                                "and li.isActive = true " +
                                "and (lim.isActive = true or lim.isActive is null) " +
                                "and (t.isActive = true or t.isActive is null) " +
                                "order by li.createdAt desc", LectureInformation.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .setParameter("coach", coach)
                .setParameter("state", state)
                .getResultList();

        // Step 4. 찾은 lecture-info 목록을 돌면서 Step 2 에서 매핑해둔 lectures 정보를 set 한다.
        for (LectureInformation lectureInformation : lectureInformations) {
            lectureInformation.setLectures(lectureInformationMap.getOrDefault(lectureInformation.getId(),
                    Collections.emptyList()));
        }

        return lectureInformations;
    }

    // 맵으로 매핑한다.
    private Map<Long, List<Lecture>> mapify(List<Lecture> lectures) {
        Map<Long, List<Lecture>> lectureInformationMap = new HashMap<>();
        for (Lecture lecture : lectures) {
            if (! lectureInformationMap.containsKey(lecture.getLectureInformation().getId())) {
                lectureInformationMap.put(lecture.getLectureInformation().getId(), new ArrayList<>());
            }
            lectureInformationMap.get(lecture.getLectureInformation().getId()).add(lecture);
        }
        return lectureInformationMap;
    }
}
