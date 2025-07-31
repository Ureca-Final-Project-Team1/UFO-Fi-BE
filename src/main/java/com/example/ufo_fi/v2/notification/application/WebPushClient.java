package com.example.ufo_fi.v2.notification.application;

import com.example.ufo_fi.v2.notification.infrastructure.firebase.dto.request.PushMassageCommand;

public interface WebPushClient {
    void sendMulticast(PushMassageCommand massageCommand);

    void sendUnicast(PushMassageCommand massageCommand);
}
