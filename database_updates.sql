
-- Remove redundant column 'address2' from 'Address'
ALTER TABLE Address DROP COLUMN address2;

-- Remove redundant column 'original_language_id' from 'Film'
ALTER TABLE Film DROP COLUMN original_language_id;

-- Merge 'film' and 'film_text' to avoid unnecessary joins
-- Add columns from 'film_text' to 'film'
ALTER TABLE Film ADD COLUMN description TEXT, ADD COLUMN title VARCHAR(255);

-- Move data from 'film_text' to 'film'
UPDATE Film f JOIN Film_Text ft ON f.film_id = ft.film_id
    SET f.description = ft.description, f.title = ft.title;

-- Remove the 'film_text' table
DROP TABLE Film_Text;

-- Create 'Special_Features' table
CREATE TABLE Special_Features (
                                  feature_id INT AUTO_INCREMENT PRIMARY KEY,
                                  feature_name VARCHAR(255) NOT NULL
);

-- Create a junction table between 'film' and 'special_features'
CREATE TABLE Film_Special_Features (
                                       film_id INT,
                                       feature_id INT,
                                       PRIMARY KEY (film_id, feature_id),
                                       FOREIGN KEY (film_id) REFERENCES Film(film_id),
                                       FOREIGN KEY (feature_id) REFERENCES Special_Features(feature_id)
);

-- Clean up the 'rating' column by removing special characters
UPDATE Film SET rating = REPLACE(rating, '-', '');