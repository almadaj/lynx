package com.schoolar.lynx.events.classes;

import java.util.UUID;

public record ClassCreatedEvent(
        UUID classId,
        String name,
        UUID companyId) { }
