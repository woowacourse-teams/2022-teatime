package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CrewAuthenticationPrincipal;
import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.service.PokeService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/pokes")
public class PokeController {

    private final PokeService pokeService;

    @PostMapping
    public ResponseEntity<Void> save(@CrewAuthenticationPrincipal Long crewId,
                                     @Valid @RequestBody PokeSaveRequest request) {
        Long pokeId = pokeService.save(crewId, request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pokeId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
