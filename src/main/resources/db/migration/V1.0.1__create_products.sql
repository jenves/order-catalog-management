CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price > 0),
    type VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);
