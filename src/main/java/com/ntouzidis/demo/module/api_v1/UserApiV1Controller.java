package com.ntouzidis.demo.module.api_v1;

import com.ntouzidis.demo.module.common.Context;
import com.ntouzidis.demo.module.common.utils.PinUtils;
import com.ntouzidis.demo.module.user.entity.CustomUserDetails;
import com.ntouzidis.demo.module.user.entity.User;
import com.ntouzidis.demo.module.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.ntouzidis.demo.module.common.constants.AuthorizationConstants.*;
import static com.ntouzidis.demo.module.common.constants.ControllerPathConstants.ID_PATH;
import static com.ntouzidis.demo.module.common.constants.ControllerPathConstants.USER_CONTROLLER_PATH;
import static com.ntouzidis.demo.module.common.constants.ParamConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = USER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class UserApiV1Controller {

  private static final String NAME_PATH = "/name";
  private static final String PASS_PATH = "/pass";
  private static final String AUTHENTICATE_PATH = "/authenticate";

  private final Context context;
  private final IUserService userService;
//  private final SimpleEncryptorService simpleEncryptorService;

  public UserApiV1Controller(Context context,
                             IUserService userService) {
    this.context = context;
    this.userService = userService;
  }

  @GetMapping
  @PreAuthorize(HAS_ADMIN_AUTHORITY)
  public ResponseEntity<List<User>> readAll() {
    return ResponseEntity.ok(userService.getAll());
  }

  @GetMapping(ID_PATH)
  @PreAuthorize(IS_AUTHENTICATED)
  public ResponseEntity<User> read(@PathVariable(ID_PARAM) Long id) {
    return ResponseEntity.ok(userService.getOne(id));
  }

  @GetMapping(NAME_PATH)
  @PreAuthorize(IS_AUTHENTICATED)
  public ResponseEntity<User> readByUsername(@RequestParam(USERNAME_PARAM) String username) {
    return ResponseEntity.ok(userService.getOneByUsername(username));
  }

  @PostMapping
  @PreAuthorize(HAS_ADMIN_AUTHORITY)
  public ResponseEntity<User> create(
      @RequestParam(USERNAME_PARAM) String username,
      @RequestParam(EMAIL_PARAM) String email,
      @RequestParam(PASS_PARAM) String pass,
      @RequestParam(CONFIRM_PASS_PARAM) String confirmPass
//      @RequestParam(PIN_PARAM) String pin
  ) {
    checkArgument(pass.equals(confirmPass), "Password doesn't match");
    checkArgument(StringUtils.isNotBlank(email) , "Wrong email");
//    PinUtils.verifyPin(pin);

    User user = userService.create(username, email, pass);

    // setting the non encrypted pass for the ui, to login immediately after creation
//    user.setPassword(confirmPass);
    return ResponseEntity.ok(user);
  }

  @PostMapping(PASS_PATH)
  public ResponseEntity<User> updatePass(
      @RequestParam(OLD_PASS_PARAM) String oldPass,
      @RequestParam(NEW_PASS_PARAM) String newPass,
      @RequestParam(CONFIRM_PASS_PARAM) String confirmPass
  ) {
    User updatedUser = userService.changePassword(context.getUserID(), newPass, confirmPass);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping(ID_PATH)
  @PreAuthorize(HAS_ADMIN_AUTHORITY)
  public ResponseEntity<List<User>> delete(@PathVariable Long id) {
      User user = userService.getOne(id);
      userService.delete(user);
      return readAll();
  }

  @GetMapping(AUTHENTICATE_PATH)
  @PreAuthorize(IS_AUTHENTICATED)
  public ResponseEntity<CustomUserDetails> authenticate() {
        return ResponseEntity.ok(context.getUserDetails());
  }
}
