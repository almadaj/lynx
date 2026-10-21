package com.schoolar.lynx.events.ClassCreated;

import java.util.UUID;

public record ClassCreatedEvent(
        UUID classId,
        String name,
        UUID companyId) { }
