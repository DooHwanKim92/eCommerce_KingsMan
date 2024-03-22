package com.example.ecommerce.domain.notice.service;


import com.example.ecommerce.domain.notice.NoticeCreateForm;
import com.example.ecommerce.domain.notice.entity.Notice;
import com.example.ecommerce.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public List<Notice> findAll() {
        return this.noticeRepository.findAll();
    }

    public void create(NoticeCreateForm noticeCreateForm) {
        Notice notice = Notice.builder()
                .category(noticeCreateForm.getCategory())
                .title(noticeCreateForm.getTitle())
                .content(noticeCreateForm.getContent())
                .build();

        this.noticeRepository.save(notice);
    }

    public Notice findById(Long id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if (notice.isEmpty()) {
            return null;
        }
        return notice.get();
    }

    public void modify(NoticeCreateForm noticeCreateForm, Notice notice) {
        Notice modifyNotice = notice.toBuilder()
                .category(noticeCreateForm.getCategory())
                .title(noticeCreateForm.getTitle())
                .content(noticeCreateForm.getContent())
                .build();

        this.noticeRepository.save(modifyNotice);
    }

    public void remove(Long id) {
        Notice notice = findById(id);

        this.noticeRepository.delete(notice);
    }
}
