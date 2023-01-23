package com.example.springbootthymeleaftw.service;

import com.example.springbootthymeleaftw.DTO.MarketUserDTO;
import com.example.springbootthymeleaftw.model.entity.Market;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.repository.MarketRepository;
import com.example.springbootthymeleaftw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class MarketValidatorService implements Validator {
    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return MarketUserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object marketEntity, Errors errors) {
        MarketUserDTO market = (MarketUserDTO) marketEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.isUsernameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.isPasswordEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"marketName","business.isNameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address","business.isAddressEmpty");

        /* Valid email regex pattern - https://owasp.org/www-community/OWASP_Validation_Regex_Repository */
        /* Typical email format: email@domain.com */
        String emailRegexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        /*
           at least 8 digits {8,}
           at least one number (?=.*\d)
           at least one lowercase (?=.*[a-z])
           at least one uppercase (?=.*[A-Z])
           at least one special character (?=.*[@#$%^&+=])
           No space [^\s]
        */
        String passwordRegexPattern = "^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*)[^\s]{8,}$";

        Boolean alreadyExistingAccount = userRepository.findByEmail(market.getEmail()).isPresent();
        Boolean isValidUserLength = !(market.getUsername().length() < 2) && !(market.getUsername().length() > 32);
        Boolean isValidEmail = market.getEmail().matches(emailRegexPattern);
        Boolean isValidPassword = market.getPassword().matches(passwordRegexPattern);
        Boolean arePasswordTheSame = market.getPassword().equals(market.getPasswordConfirm());
        Boolean alreadyExistingIdCode=marketRepository.findMarketByIdentificationCode(market.getIdentificationCode()).isPresent();

        if(alreadyExistingIdCode)
            errors.rejectValue("identificationCode","business.idCodeAlreadyExisting");
        if (!isValidUserLength)
            errors.rejectValue("username", "user.isValidUserLength");
        if (alreadyExistingAccount)
            errors.rejectValue("email", "user.isUsedEmail");
        if (!isValidEmail)
            errors.rejectValue("email", "user.isValidEmail");
        if (!isValidPassword)
            errors.rejectValue("password", "user.isValidPassword");
        if (!arePasswordTheSame)
            errors.rejectValue("passwordConfirm", "user.isPasswordTheSame");

    }
}
