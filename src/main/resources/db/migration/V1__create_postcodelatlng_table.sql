CREATE TABLE IF NOT EXISTS postcodelatlng
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    postcode
    VARCHAR
(
    255
),
    latitude DECIMAL
(
    10,
    8
),
    longitude DECIMAL
(
    11,
    8
),
    CONSTRAINT pk_postcodelatlng PRIMARY KEY
(
    id
)
    );


