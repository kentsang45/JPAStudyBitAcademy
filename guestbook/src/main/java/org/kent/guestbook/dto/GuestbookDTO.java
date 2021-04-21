package org.kent.guestbook.dto;

import lombok.*;

import javax.persistence.Access;
import java.time.LocalDateTime;


@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookDTO {
    private Long gno;

    private String title;
    private String content;
    private String writer;

    private LocalDateTime regDate, modDate;
}
