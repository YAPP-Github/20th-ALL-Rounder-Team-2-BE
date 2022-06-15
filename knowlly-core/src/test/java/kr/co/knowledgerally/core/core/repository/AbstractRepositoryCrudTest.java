package kr.co.knowledgerally.core.core.repository;

import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@KnowllyDataTest
public abstract class AbstractRepositoryCrudTest {

    @Autowired
    protected EntityManager entityManager;

    protected abstract void selectTest();
    protected abstract void insertTest();
    protected abstract void updateTest();
    protected abstract void deleteTest();
}
