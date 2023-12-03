package com.project.LWBS.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoDTO {
    private long id;
    private String nickname;
    private String profile_img;
}
