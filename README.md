# Project-Hibernate-2
## Suggestions for improvement
Remove redundant column address2 in table Address as it is not used in database at all.

Remove redundant column original_language_id in table Film.

It would be better to merge film and film_text to prevent unnecessary joins.

Create a separate table for special_features instead of using a set.

Use column rating without ("-") special characters to make it easier to adjust.