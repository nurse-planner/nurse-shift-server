package com.nurseshift.shift.nurse;

import com.nurseshift.shift.member.authentication.MemberPrincipal;
import com.nurseshift.shift.nurse.off.Off;
import com.nurseshift.shift.nurse.off.OffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nurse")
public class NurseController {

    private final NurseService nurseService;
    private final NurseMapper nurseMapper;
    private final OffMapper offMapper;

    @PostMapping
    public ResponseEntity<NurseDto.Response> createNurse(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                         @RequestBody NurseDto.Post post) {
        Nurse nurse = nurseMapper.postToEntity(post);
        List<Off> offs = offMapper.requestToEntity(post.getOffs(), true);
        List<Off> rests = offMapper.requestToEntity(post.getRests(), false);
        Nurse createdNurse = nurseService.createNurse(nurse, memberPrincipal.getMember(), offs, rests);
        NurseDto.Response response = nurseMapper.entityToResponse(createdNurse);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<NurseDto.Response> updateNurse(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                         @RequestBody NurseDto.Patch patch) {
        Nurse nurse = nurseMapper.patchToEntity(patch);
        List<Off> offs = offMapper.requestToEntity(patch.getOffs(), true);
        List<Off> rests = offMapper.requestToEntity(patch.getRests(), false);
        Nurse updatedNurse = nurseService.updateNurse(nurse, memberPrincipal.getMember(), offs, rests);
        NurseDto.Response response = nurseMapper.entityToResponse(updatedNurse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{nurse-id}")
    public ResponseEntity<NurseDto.Response> getNurse(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                      @PathVariable("nurse-id") Long id) {
        Nurse nurse = nurseService.getNurse(id, memberPrincipal.getMember());
        NurseDto.Response response = nurseMapper.entityToResponse(nurse);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<NurseDto.Response>> getNurses(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        List<Nurse> nurses = nurseService.getNurses(memberPrincipal.getMember());
        List<NurseDto.Response> responses = nurseMapper.entitiesToResponses(nurses);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNurse(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                         @RequestBody NurseDto.Delete delete) {
        nurseService.deleteNurse(delete.getId(), memberPrincipal.getMember());
        return ResponseEntity.noContent().build();
    }
}
