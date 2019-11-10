package com.ntouzidis.demo.module.api_v1;

import com.ntouzidis.demo.module.user.dto.SetupDetails;
import com.ntouzidis.demo.module.user.service.SetupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntouzidis.demo.module.common.constants.ControllerPathConstants.SETUP_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(
    value = SETUP_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class SetupApi {

  private final SetupService setupService;

  public SetupApi(SetupService setupService) {
    this.setupService = setupService;
  }

  @PostMapping
  public ResponseEntity<SetupDetails> initialSetup() {
    SetupDetails setupDetails = setupService.initialSetup();
    return ResponseEntity.ok(setupDetails);
  }
}
