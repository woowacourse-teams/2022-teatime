package com.woowacourse.teatime.teatime.service;

public enum AlarmTitle {

    APPLY("☕️티타임이 신청되었습니다.", "#CCE6BA"),
    CONFIRM("✅티타임이 확정되었습니다.", "#B4E0FF"),
    CANCEL("🥹아이쿠...! 티타임이 취소되었습니다..", "#E6ACA8"),
    REMIND_CREW("⏰내일 티타임이 있습니다. 시트를 잊지 말고 제출해주세요!!", "#FFE594"),
    REMIND_COACH("⏰내일 티타임이 있습니다.", "#FF2594");

    private final String message;
    private final String barColor;

    AlarmTitle(String message, String barColor) {
        this.message = message;
        this.barColor = barColor;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return message;
    }

    public String getBarColor() {
        return barColor;
    }
}
