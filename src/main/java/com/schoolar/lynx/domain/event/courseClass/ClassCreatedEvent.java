package com.schoolar.lynx.domain.event.courseClass;

import java.util.UUID;

public record ClassCreatedEvent(
        UUID classId,
        String name,
        UUID companyId) { }
