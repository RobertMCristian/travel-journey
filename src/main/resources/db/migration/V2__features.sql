CREATE TABLE entry_locations (
                                 entry_id BIGINT PRIMARY KEY,
                                 country VARCHAR(100),
                                 city VARCHAR(100),
                                 place_name VARCHAR(200),
                                 latitude DOUBLE PRECISION,
                                 longitude DOUBLE PRECISION,
                                 CONSTRAINT fk_entry_locations_entry
                                    FOREIGN KEY (entry_id) REFERENCES entries(id)
                                    ON DELETE CASCADE
);

CREATE TABLE tags (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE entry_tags (
                            entry_id BIGINT NOT NULL,
                            tag_id BIGINT NOT NULL,
                            PRIMARY KEY (entry_id, tag_id),
                            CONSTRAINT fk_entry_tags_entry
                            FOREIGN KEY (entry_id) REFERENCES entries(id)
                            ON DELETE CASCADE,
                            CONSTRAINT fk_entry_tags_tag
                            FOREIGN KEY (tag_id) REFERENCES tags(id)
                            ON DELETE CASCADE
);

CREATE INDEX idx_tags_name ON tags(name);

CREATE TABLE journal_collaborators (
                                       journal_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       role VARCHAR(30) NOT NULL, -- VIEWER / EDITOR
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (journal_id, user_id),
                                       CONSTRAINT fk_jc_journal
                                        FOREIGN KEY (journal_id) REFERENCES journals(id)
                                        ON DELETE CASCADE,
                                       CONSTRAINT fk_jc_user
                                        FOREIGN KEY (user_id) REFERENCES users(id)
                                        ON DELETE CASCADE
);

CREATE INDEX idx_jc_user_id ON journal_collaborators(user_id);

CREATE TABLE expenses (
                          id BIGSERIAL PRIMARY KEY,
                          entry_id BIGINT NOT NULL,
                          amount NUMERIC(12,2) NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          category VARCHAR(60) NOT NULL,
                          note VARCHAR(255),
                          expense_date DATE,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_expenses_entry
                            FOREIGN KEY (entry_id) REFERENCES entries(id)
                            ON DELETE CASCADE
);

CREATE INDEX idx_expenses_entry_id ON expenses(entry_id);
CREATE INDEX idx_expenses_expense_date ON expenses(expense_date);

CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               type VARCHAR(50) NOT NULL,
                               message VARCHAR(255) NOT NULL,
                               payload JSONB,
                               is_read BOOLEAN NOT NULL DEFAULT FALSE,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_notifications_user
                                FOREIGN KEY (user_id) REFERENCES users(id)
                                ON DELETE CASCADE
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
