package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {
  @ExceptionHandler(PasswordIsErrorException.class)
  public ResponseEntity handlePasswordIsErrorException(PasswordIsErrorException exception) {
    String str = "密码错误，请重新输入";
    return ResponseEntity.badRequest().body(str);
  }

  @ExceptionHandler(UserHasExistedException.class)
  public ResponseEntity handleUserHasExistedException(UserHasExistedException exception) {
    String str = "手机号已注册，请输入其他手机号码。";
    return ResponseEntity.badRequest().body(str);
  }

  @ExceptionHandler(UserNotExistedException.class)
  public ResponseEntity handlePackageNotExistedException(UserNotExistedException exception) {
    String str = "用户不存在，请重新输入。";
    return ResponseEntity.badRequest().body(str);
  }

  @ExceptionHandler(ClerkIsFrozenException.class)
  public ResponseEntity handleClerkIsFrozenException(ClerkIsFrozenException exception) {
    String str = "账号已被冻结。";
    return ResponseEntity.badRequest().body(str);
  }

  @ExceptionHandler(NoAuthException.class)
  public ResponseEntity handleNoAuthException(NoAuthException exception) {
    String str = "未经授权。";
    return ResponseEntity.badRequest().body(str);
  }


  @ExceptionHandler(NoFeezeException.class)
  public ResponseEntity handleClerkIsFrozenException(NoFeezeException exception) {
    String str = "账号尚有未完成订单，不能被冻结。";
    return ResponseEntity.badRequest().body(str);
  }
}
