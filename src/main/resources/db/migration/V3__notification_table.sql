CREATE SCHEMA evento;
CREATE TABLE evento.notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    title VARCHAR(150),

    company_id UUID NOT NULL,

    type VARCHAR(50) NOT NULL,

    message VARCHAR(300),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_company
        FOREIGN KEY (company_id)
        REFERENCES organizacao.company (id)
);

CREATE INDEX idx_notification_company_created_at
    ON evento.notification (company_id, created_at DESC);


CREATE TABLE evento.notification_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    notification_id UUID NOT NULL,

    user_id UUID NOT NULL,

    read_at TIMESTAMP NULL,

    CONSTRAINT fk_notification_user_notification
        FOREIGN KEY (notification_id)
        REFERENCES evento.notification (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_notification_user_user
        FOREIGN KEY (user_id)
        REFERENCES seguranca.users (id),

    CONSTRAINT uk_notification_user
        UNIQUE (notification_id, user_id)
);

CREATE INDEX idx_notification_user_user_read
    ON evento.notification_user (user_id, read_at);
