package com.example.model.respond;

import com.example.utiliy.DateTranslation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoReadMessage {

    private String userId;

    private String content;

    private Date time;

//    private LocalDateTime localDate;
//
//    private void translationData(){
//
//        this.localDate = DateTranslation.dateTranslationLocalDateTime(time);
//
//    }
}
