package com.service.mail.Mail.Service.domain.model;

public record EmailDto(
        String receiver,
        String subject,
        String text
) {
}
