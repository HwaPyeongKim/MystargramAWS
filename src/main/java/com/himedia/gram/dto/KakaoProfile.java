package com.himedia.gram.dto;

import lombok.Data;

@Data
public class KakaoProfile {

    private String id;
    private String connected_at;
    private KakaoAccount kakao_account;

    @Data
    public class KakaoAccount {
        private Profile profile;
        //private String email;
        //private boolean has_email;
        private boolean profile_nickname_needs_agreement;
        private boolean profile_image_needs_agreement;

        @Data
        public class Profile {
            private String nickname;
            private String profile_image_url;
            private String thumbnail_image_url;
        }
    }

}
