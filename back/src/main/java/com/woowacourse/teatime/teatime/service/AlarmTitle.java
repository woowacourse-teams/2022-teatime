package com.woowacourse.teatime.teatime.service;

public enum AlarmTitle {

    APPLY("☕️ 티타임이 신청되었습니다.", "#CCE6BA"),
    CONFIRM("✅ 티타임이 확정되었습니다.", "#B4E0FF"),
    SUBMIT_SHEET_TO_CREW("📝 시트가 제출되었습니다.", "#9498FF"),
    SUBMIT_SHEET_TO_COACH("📝 방금 크루가 시트를 제출했어요! 한번 확인하러 가볼까요?", "#9498FF"),
    CANCEL("🥹 아이쿠...! 티타임이 취소되었습니다..", "#E6ACA8"),
    REMIND_CREW("⏰ 내일 티타임이 있습니다. 시트를 잊지 말고 제출해주세요!!", "#FFE594"),
    REMIND_COACH("⏰ 내일 티타임이 있습니다.", "#FFE594");

    private final String message;
    private final String barColor;

    AlarmTitle(String message, String barColor) {
        this.message = message;
        this.barColor = barColor;
    }

    public String getMessage() {
        return message;
    }

    public String getBarColor() {
        return barColor;
    }
}
