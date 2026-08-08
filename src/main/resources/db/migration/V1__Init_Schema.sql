-- =========================================================
-- Esquema MVP: aplicacion de control remoto de escritorio
-- Motor: PostgreSQL 15+
-- =========================================================

CREATE EXTENSION IF NOT EXISTS "pgcrypto"; -- para gen_random_uuid()

-- Organizaciones (ej: el call center como cliente de la plataforma)
CREATE TABLE organization (
                               id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               name            VARCHAR(150) NOT NULL,
                               created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Usuarios: tanto tecnicos (controladores) como administradores
CREATE TABLE users (
                       id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       organization_id UUID NOT NULL REFERENCES organization(id) ON DELETE CASCADE,
                       email           VARCHAR(255) NOT NULL UNIQUE,
                       password_hash   VARCHAR(255) NOT NULL,
                       full_name       VARCHAR(150) NOT NULL,
                       role            VARCHAR(20)  NOT NULL DEFAULT 'USER',
                                        CHECK ( role IN ('USER', 'ADMIN', 'SUPPORT') ),
                       is_active       BOOLEAN NOT NULL DEFAULT true,
                       created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_user_org ON users(organization_id);

-- Dispositivos: los PCs con el agente host instalado
CREATE TABLE device (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         organization_id UUID NOT NULL REFERENCES organization(id) ON DELETE CASCADE,
                         device_code     VARCHAR(20) NOT NULL UNIQUE,   -- ID visible tipo "123 456 789"
                         hostname        VARCHAR(150),
                         operatingSystem VARCHAR(50),                    -- windows / linux / macos
                         os_version      VARCHAR(50),
                         agent_version   VARCHAR(20),
                         public_key      TEXT NOT NULL,                  -- clave publica para cifrado de sesion
                         unattended_hash VARCHAR(255),                    -- hash del password de acceso desatendido, null si no aplica
                         last_seen_at    TIMESTAMPTZ,
                         last_ip         INET,
                         created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_device_org ON device(organization_id);
CREATE INDEX idx_device_code ON device(device_code);

-- Tokens de acceso temporal (PIN de sesion, expira)
CREATE TABLE access_token (
                               id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               device_id       UUID NOT NULL REFERENCES device(id) ON DELETE CASCADE,
                               pin_hash        VARCHAR(255) NOT NULL,
                               expires_at      TIMESTAMPTZ NOT NULL,
                               used_at         TIMESTAMPTZ,
                               created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_access_token_device ON access_token(device_id);

-- Sesiones de control remoto (auditoria completa)
CREATE TABLE remote_session (
                                 id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 device_id           UUID NOT NULL REFERENCES device(id) ON DELETE CASCADE,
                                 technician_id       UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                 connection_type     VARCHAR(20) NOT NULL DEFAULT 'P2P'
                                     CHECK (connection_type IN ('P2P', 'RELAY')),
                                 started_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
                                 ended_at            TIMESTAMPTZ,
                                 end_reason          VARCHAR(50),                 -- 'closed_by_technician', 'closed_by_host', 'timeout', 'error'
                                 avg_latency_ms      INTEGER,
                                 bytes_transferred   BIGINT
);

CREATE INDEX idx_session_device ON remote_session(device_id);
CREATE INDEX idx_session_technician ON remote_session(technician_id);
CREATE INDEX idx_session_started ON remote_session(started_at);

-- Eventos dentro de una sesion (para auditoria fina, opcional en MVP)
CREATE TABLE session_event (
                                id              BIGSERIAL PRIMARY KEY,
                                session_id      UUID NOT NULL REFERENCES remote_session(id) ON DELETE CASCADE,
                                event_type      VARCHAR(30) NOT NULL,            -- 'file_transfer', 'clipboard_sync', 'reconnect'
                                metadata        JSONB,
                                occurred_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_session_events_session ON session_event(session_id);