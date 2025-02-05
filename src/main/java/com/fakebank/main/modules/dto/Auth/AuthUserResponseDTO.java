package com.fakebank.main.modules.dto.Auth;

public class AuthUserResponseDTO {
    private String access_token;
    private Long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    private AuthUserResponseDTO(String access_token, Long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }

    public static AuthUserResponseDTOBuilder builder() {
        return new AuthUserResponseDTOBuilder();
    }

    public static class AuthUserResponseDTOBuilder {
        private String access_token;
        private Long expires_in;

        public AuthUserResponseDTOBuilder accessToken(String access_token) {
            this.access_token = access_token;
            return this;
        }

        public AuthUserResponseDTOBuilder expiresIn(Long expires_in) {
            this.expires_in = expires_in;
            return this;
        }

        public AuthUserResponseDTO build() {
            return new AuthUserResponseDTO(access_token, expires_in);
        }
    }
}

