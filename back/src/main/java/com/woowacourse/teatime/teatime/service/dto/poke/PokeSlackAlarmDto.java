package com.woowacourse.teatime.teatime.service.dto.poke;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PokeSlackAlarmDto {

    private static final String DIRECT_LINK = "<https://teatime.pe.kr/|티타임 바로가기>";
    private static final String TYPE_SECTION = "section";
    private static final String TYPE_DIVIDER = "divider";
    private static final String TYPE_IMAGE = "image";
    private static final String MARK_DOWN = "mrkdwn";
    private static final String IMAGE_URL = "https://user-images.githubusercontent.com/52141636/195832482-f11381d0-d13b-4243-b5d1-4da9f8eb6643.png";
    private static final String ALT_TEXT = "postman";

    private String channel;
    private List<Object> blocks;

    public static PokeSlackAlarmDto pokeToCoach(String crewName, String coachSlackId) {
        List<Object> blocks = new ArrayList<>();
        TextBlock textBlock = new TextBlock(TYPE_SECTION,
                new TextDto(MARK_DOWN, String.format(":point_left: *아얏! %s님이 방금 콕 찔렀어요!!*", crewName)));
        DividerBlock dividerBlock = new DividerBlock(TYPE_DIVIDER);
        TextWithImageBlock textWithImageBlock = new TextWithImageBlock(TYPE_SECTION,
                new TextDto(MARK_DOWN, String.format(
                        ":date: *%s님을 위한 일정을 등록하러 가볼까요?* \n%s\n\n더이상 요청을 받고 싶지 않고싶다면 티타임 내 프로필에서 설정할 수 있어요.",
                        crewName,
                        DIRECT_LINK)),
                new AccessoryDto(TYPE_IMAGE, IMAGE_URL, ALT_TEXT));

        blocks.add(textBlock);
        blocks.add(dividerBlock);
        blocks.add(textWithImageBlock);

        return new PokeSlackAlarmDto(coachSlackId, blocks);
    }
}
