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

    private String channel;
    private List<Object> blocks;

    public static PokeSlackAlarmDto pokeToCoach(PokeAlarmInfoDto pokeAlarmDto) {
        String crewName = pokeAlarmDto.getCrewName();
        String coachSlackId = pokeAlarmDto.getCoachSlackId();

        List<Object> blocks = new ArrayList<>();

        BlockDto1 blockDto1 = new BlockDto1("section",
                new TextDto("mrkdwn", ":point_left: *아얏! " + crewName + "님이 방금 쿡 찔렀어요!!*"));

        BlockDto2 blockDto2 = new BlockDto2("divider");

        BlockDto3 blockDto3 = new BlockDto3("section",
                new TextDto("mrkdwn", ":date: *" + crewName
                        + "님을 위한 일정을 등록하러 가볼까요?* \n<https://teatime.pe.kr/|티타임 바로가기>\n\n:neutral_face: 더이상 요청을 받고 싶지 않고싶다면 티타임 내 프로필에서 설정할 수 있어요."),
                new AccessoryDto("image",
                        "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4d1f389a-f267-4a54-a1e3-2354b69cb436/dd79g53-2d45f61e-6909-4e9b-aff0-38ae288ffef1.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzRkMWYzODlhLWYyNjctNGE1NC1hMWUzLTIzNTRiNjljYjQzNlwvZGQ3OWc1My0yZDQ1ZjYxZS02OTA5LTRlOWItYWZmMC0zOGFlMjg4ZmZlZjEuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.naHFAbVbUXFKW1u10-zbmv0uZcjqAWUzWoyzbKd_dmQ",
                        "postman"));

        blocks.add(blockDto1);
        blocks.add(blockDto2);
        blocks.add(blockDto3);

        return new PokeSlackAlarmDto(coachSlackId, blocks);
    }
}
