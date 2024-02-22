package com.example.ecommerce.domain.alarm.service;

import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.alarm.repository.AlarmRepository;
import com.example.ecommerce.domain.answer.entity.Answer;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;


    public Alarm confirmDenyAlarm(SiteUser user, Confirm confirm) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .title("판매 권한 신청이 거절되었습니다.")
                .content("사업자명 : " + confirm.getSellerName() + "\n사업자번호 : " + confirm.getSellerNumber() + "\n (으)로 신청한 판매권한 신청이 거절되었습니다.")
                .isChecked(false)
                .build();

        this.alarmRepository.save(alarm);
        return alarm;
    }

    public Alarm confirmAcceptAlarm(SiteUser user, Confirm confirm) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .title("판매 권한 신청이 승인되었습니다.")
                .content("사업자명 : " + confirm.getSellerName() + "\n사업자번호 : " + confirm.getSellerNumber() + "\n (으)로 신청한 판매권한 신청이 승인되었습니다.")
                .isChecked(false)
                .build();

        this.alarmRepository.save(alarm);
        return alarm;
    }

    public List<Alarm> findAll() {
        return this.alarmRepository.findAll();
    }

    public Alarm findById(Long id) {
        Optional<Alarm> alarm = this.alarmRepository.findById(id);
        if (alarm.isEmpty()) {
            return null;
        }
        return alarm.get();
    }

    public void check(Alarm alarm) {
        Alarm checkAlarm = alarm.toBuilder()
                .isChecked(true)
                .build();

        this.alarmRepository.save(checkAlarm);
    }

    public void remove(Alarm alarm) {
        this.alarmRepository.delete(alarm);
    }

    public void questionAnswered(Answer answer, SiteUser user) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .title("문의하셨던 내용에 대한 답변이 작성되었습니다.")
                .content("답변 : " + answer.getContent())
                .isChecked(false)
                .build();

        this.alarmRepository.save(alarm);
    }
}
