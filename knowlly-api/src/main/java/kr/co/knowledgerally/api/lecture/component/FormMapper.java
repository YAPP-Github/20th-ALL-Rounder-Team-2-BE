package kr.co.knowledgerally.api.lecture.component;

import kr.co.knowledgerally.api.lecture.dto.FormDto;
import kr.co.knowledgerally.api.lecture.dto.LectureDto;
import kr.co.knowledgerally.api.user.component.UserImageMapper;
import kr.co.knowledgerally.api.user.component.UserMapper;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.user.service.UserImageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class, LectureMapper.class }
)
public abstract class FormMapper {
    @Autowired
    protected UserImageMapper userImageMapper;

    @Autowired
    protected UserImageService userImageService;

    @Mapping(target = "expirationDate", expression = "java(form.getExpirationDate().toString())")
    @Mapping(target = "userImage", expression = "java(userImageMapper.toDto(userImageService.findByUser(form.getUser())))")
    public abstract FormDto.ReadOnly toDto(Form form);
    public abstract Form toEntity(FormDto lectureDto);
}
