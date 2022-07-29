package kr.co.knowledgerally.batch.core.job;

/**
 * 이 인터페이스를 구현하여 빈으로 등록해두면,
 * {@link kr.co.knowledgerally.batch.BatchStarter}에 의해 자동으로 등록됨
 *
 * @see kr.co.knowledgerally.batch.BatchStarter
 */
public interface JobRequestService {
    JobRequest create();
}
