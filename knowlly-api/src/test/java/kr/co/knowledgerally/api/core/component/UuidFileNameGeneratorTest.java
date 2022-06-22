package kr.co.knowledgerally.api.core.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UuidFileNameGeneratorTest {
    @Test
    void Uuid_이름_생성_테스트() {
        String originalFileName = "원본파일이름";
        FileNameGenerator fileNameGenerator = new UuidFileNameGenerator();

        String generated = fileNameGenerator.generate(originalFileName);
        System.out.println(generated);
        assertTrue(generated.endsWith("_원본파일이름"));
    }
}