CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(255) NOT NULL,
    discount DOUBLE PRECISION CHECK (discount >= 0)
);