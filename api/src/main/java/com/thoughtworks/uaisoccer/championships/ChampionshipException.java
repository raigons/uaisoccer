package com.thoughtworks.uaisoccer.championships;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid id")
public class ChampionshipException extends RuntimeException {

}
