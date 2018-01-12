USE treasure_db;

# setting categories
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE categories;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO categories (category)

    VALUES
      ('accessories'),
      ('antiques'),
      ('appliances'),
      ('automobiles'),
      ('bicycles'),
      ('business'),
      ('books'),
      ('cameras'),
      ('children'),
      ('clothes'),
      ('electronics'),
      ('furniture'),
      ('garden'),
      ('household'),
      ('instruments'),
      ('movies'),
      ('music'),
      ('outdoor'),
      ('pets'),
      ('phones'),
      ('tickets'),
      ('toys'),
      ('sports'),
      ('MISC.');

