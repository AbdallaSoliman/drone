/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musala.drones.message.util;

import com.musala.drones.message.constant.MessageType;
import com.musala.drones.message.RestMessage;
import com.musala.drones.message.constant.MessagesCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
@Scope("singleton")
public class MessageUtilities {


    @Cacheable("ResourceBundleMessage")
    public String getMessage(String key) {

        return ResourceBundle.getBundle("i18n/messages", LocaleContextHolder.getLocale())
                .getString(key);
    }
    public String getFormattedMessage(String messageKey, Object... objects) {

        return String.format(getMessage(messageKey), objects);
    }

    public ResponseEntity<RestMessage> getSuccessCustomMessage(String operationName) {
      return new ResponseEntity<>(new RestMessage(getFormattedMessage(MessagesCode.SUCCESS_OPERATION_CUSTOM,getMessage(operationName)),MessageType.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<RestMessage> getSuccessMessage(String msg) {
        return new ResponseEntity<>(new RestMessage(getMessage(msg), MessageType.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<RestMessage> getFormattedSuccessMessage(String messageKey, Object... objects) {
        return new ResponseEntity<>(new RestMessage(getFormattedMessage(messageKey,objects),MessageType.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<RestMessage> getFailMessage(String msg) {
        return new ResponseEntity<>(new RestMessage(getMessage(msg),MessageType.VALIDATION), HttpStatus.BAD_REQUEST);
    }
}
