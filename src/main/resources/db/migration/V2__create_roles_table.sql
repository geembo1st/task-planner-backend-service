CREATE TABLE IF NOT EXISTS roles(id SERIAL PRIMARY KEY,
                                name VARCHAR(50) NOT NULL UNIQUE,
                                description VARCHAR(255)
);