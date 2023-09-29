package com.nurseshift.shift.nurse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nurse")
public class NurseController {

    private final NurseService nurseService;
    private final NurseMapper nurseMapper;

    @PostMapping
    public ResponseEntity<NurseDto.Response> createNurse(@RequestBody NurseDto.Post post) {
        Nurse nurse = nurseMapper.postToEntity(post);
        Nurse createdNurse = nurseService.createNurse(nurse);
        NurseDto.Response response = nurseMapper.entityToResponse(createdNurse);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getNurses(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            List<Nurse> nurses = nurseService.getNurses();
            List<NurseDto.Response> responses = nurseMapper.entitiesToResponses(nurses);
            return ResponseEntity.ok(responses);
        } else {
            Nurse nurse = nurseService.getNurse(id);
            NurseDto.Response response = nurseMapper.entityToResponse(nurse);
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNurse(@RequestBody NurseDto.Delete delete) {
        nurseService.deleteNurse(delete.getId());
        return ResponseEntity.noContent().build();
    }
}
