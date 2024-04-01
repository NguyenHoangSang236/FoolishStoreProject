package com.backend.core.usecase.business.authentication;

import com.backend.core.entity.account.model.Account;
import com.backend.core.entity.api.ApiResponse;
import com.backend.core.infrastructure.business.account.repository.AccountRepository;
import com.backend.core.usecase.UseCase;
import com.backend.core.usecase.statics.AccountStatusEnum;
import com.backend.core.usecase.util.ValueRenderUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ForgotPasswordUseCase extends UseCase<ForgotPasswordUseCase.InputValue, ApiResponse> {
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ValueRenderUtils valueRenderUtils;

    @Override
    public ApiResponse execute(InputValue input) {
        String username = input.getUserName();
        String email = input.getEmail();

        Account currentAccount = accountRepo.getAccountByUserName(username);

        if (currentAccount != null) {
            if (currentAccount.getStatus().equals(AccountStatusEnum.BANNED.name())) {
                return new ApiResponse("failed", "This account has been banned", HttpStatus.BAD_REQUEST);
            } else if (currentAccount.getCustomer().getEmail().equals(email)) {
                String newPassword = getNewTemporaryRandomPasswordMail(username, email);

                String newEncodedPassword = passwordEncoder.encode(newPassword);
                currentAccount.setPassword(newEncodedPassword);
                accountRepo.save(currentAccount);

                return new ApiResponse("success", "Change password successfully", HttpStatus.OK);
            } else
                return new ApiResponse("failed", "This email does not belong to this account", HttpStatus.BAD_REQUEST);
        } else
            return new ApiResponse("failed", "This account is not existed", HttpStatus.BAD_REQUEST);
    }

    private String getNewTemporaryRandomPasswordMail(String userName, String email) {
        String fromAddress = "foolishstore2@gmail.com";
        String senderName = "Fool!sh Fashion Store";
        String subject = "Your new temporary password";
        String newTempPassoword = valueRenderUtils.randomTemporaryPassword(userName);
        String content = "Dear [[name]],<br>"
                + "Your new temporary password is " + newTempPassoword + "<br>"
                + "Please remember to change a new password for your new account because this temporary password will be changed after you close the website !!<br><br>"
                + "Thank you,<br>"
                + "Fool!sh Fashion Store";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(email);
            helper.setSubject(subject);

            content = content.replace("[[name]]", userName);

            helper.setText(content, true);

            mailSender.send(message);

            return newTempPassoword;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Value
    public static class InputValue implements InputValues {
        String userName;
        String email;
    }
}
