package com.woowacourse.teatime.teatime.service;

public enum AlarmTitle {

    APPLY("☕️티타임이 신청되었습니다."),
    CONFIRM("✅티타임이 확정되었습니다."),
    CANCEL("🥹아이쿠...! 티타임이 취소되었습니다.."),
    REMIND_CREW("⏰내일 티타임이 있습니다. 시트를 잊지 말고 제출해주세요!!"),
    REMIND_COACH("⏰내일 티타임이 있습니다.");

    private final String message;

    AlarmTitle(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return message;
    }
}
